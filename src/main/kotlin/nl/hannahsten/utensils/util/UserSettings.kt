package nl.hannahsten.utensils.util

import nl.hannahsten.utensils.util.UserSettings.autoWrite
import nl.hannahsten.utensils.util.UserSettings.folderName
import nl.hannahsten.utensils.util.UserSettings.write
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.util.*

/**
 * A simple way to have global user settings for your program.
 *
 * The file where the settings are stored is called `user.settings` and is located in the [folderName] folder in the
 * user's home directory on the system. You can set the directory name like this:
 * `UserSettings.folderName = ".myfoldername"`.
 *
 * By default, every time you add a new value the properties file gets saved to the `user.settings` file.
 * To disable this, set [autoWrite] to `false`. This does mean that you are responsible for saving yourself which
 * you can do using [write].
 *
 * @author Hannah Schellekens
 */
object UserSettings {

    /**
     * The name of the folder in the user's home directory where the settings get stored.
     *
     * Does not automatically include a '.', so you have to provide one yourself if you want to.
     */
    public lateinit var folderName: String

    /**
     * The path of the directory where the settings file is stored in.
     */
    public val directoryPath
        get() = "${System.getProperty("user.home")}/$folderName"

    /**
     * The path of the properties file where the settings are stored.
     */
    public val filePath
        get() = "$directoryPath/user.settings"

    /**
     * Whether the properties must automatically be written to the properties file whenever a value gets mutated (`true`)
     * or not (`false`).
     */
    public var autoWrite = true

    /**
     * Java's [Properties] object where the settings are stored in.
     */
    private val properties = Properties()

    /**
     * `true` when the settings object has been initialised.
     */
    private var initialised = false

    private fun initialise() {
        // Make sure UserSettings only gets initialised once.
        if (initialised) return

        // Check if there is a folder name given.
        check(::folderName.isInitialized) { "The folder name has not been initialised!" }

        createSettingsDirectory()

        val propertiesFile = File(filePath)
        if (!propertiesFile.exists()) {
            writePropertiesFile()
        }

        loadPropertiesFile()
    }

    /**
     * Saves value `value` in the user settings.
     */
    fun store(key: String, value: String) {
        initialise()
        properties.setProperty(key, value)

        if (autoWrite) {
            writePropertiesFile()
        }
    }

    /**
     * Loads the value of a given `key` from the user setting.
     */
    fun load(key: String): String? {
        initialise()
        return properties.getProperty(key)
    }

    /**
     * Saves the current state of the settings to the properties file.
     */
    fun write() {
        initialise()
        writePropertiesFile()
    }

    private fun loadPropertiesFile() {
        val input = FileInputStream(filePath)
        input.use {
            properties.load(input)
        }
    }

    private fun writePropertiesFile() {
        createSettingsDirectory()
        val output = FileWriter(filePath)
        output.use {
            properties.store(output, null)
        }
    }

    private fun createSettingsDirectory() {
        val directory = File(directoryPath)
        if (directory.exists().not()) {
            directory.mkdirs()
        }
    }

    operator fun get(key: String) = load(key)
    operator fun set(key: String, value: String) = store(key, value)
}