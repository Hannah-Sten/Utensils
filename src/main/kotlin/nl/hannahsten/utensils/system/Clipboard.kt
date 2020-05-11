package nl.hannahsten.utensils.system

import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection

/**
 * @author Hannah Schellekens
 */
object Clipboard {

    /**
     * Get the current contents of the clipboard as string.
     */
    @JvmStatic
    val stringContents: String
        get() {
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            return clipboard.getData(DataFlavor.stringFlavor) as String
        }

    /**
     * Set the clipboard value to the given string value.
     */
    @JvmStatic
    fun set(value: String) {
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        val selection = StringSelection(value)
        clipboard.setContents(selection, selection)
    }
}