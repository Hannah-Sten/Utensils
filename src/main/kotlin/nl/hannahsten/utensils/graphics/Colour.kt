package nl.hannahsten.utensils.graphics

import nl.hannahsten.utensils.math.matrix.FloatVector
import java.util.*

/**
 * Class containing 100 colour constants in `FloatVector` format (all without transparency [alpha = 1.0f]).
 *
 * @author Hannah Schellekens
 */
object Colour {

    /**
     * <div style='color:white;background-color:#E0218A;margin-right:4px;margin-top:4px;padding:4px;'>Barbie
     * Pink</div>
     */
    @JvmField
    val BARBIE_PINK = FloatVector(224f / 255, 33f / 255, 138f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#39A78E;margin-right:4px;margin-top:4px;padding:4px;'>Zomp</div>
     */
    @JvmField
    val ZOMP = FloatVector(57f / 255, 167f / 255, 173f / 255, 1.0f)
    
    /**
     * <div style='background-color:#A4F4F9;margin-right:4px;margin-top:4px;padding:4px;'>Waterspout</div>
     */
    @JvmField
    val WATERSPOUT = FloatVector(0.64f, 0.96f, 0.98f, 1.0f)
    
    /**
     * <div style='background-color:#E2725B;margin-right:4px;margin-top:4px;padding:4px;'>Terra
     * Cotta</div>
     */
    @JvmField
    val TERRA_COTTA = FloatVector(0.89f, 0.45f, 0.36f, 1.0f)
    
    /**
     * <div style='color:white;background-color:#008080;margin-right:4px;margin-top:4px;padding:4px;'>Teal</div>
     */
    @JvmField
    val TEAL = FloatVector(0f, 0.5f, 0.5f, 1.0f)
    
    /**
     * <div style='color:white;background-color:#704214;margin-right:4px;margin-top:4px;padding:4px;'>Sepia</div>
     */
    @JvmField
    val SEPIA = FloatVector(112f / 255, 66f / 255, 20f / 255, 1.0f)
    
    /**
     * <div style='background-color:#FFD800;margin-right:4px;margin-top:4px;padding:4px;'>School Bus
     * Yellow</div>
     */
    @JvmField
    val SCHOOL_BUS_YELLOW = FloatVector(1.0f, 216f / 255, 0f, 1.0f)
    
    /**
     * <div style='background-color:#50C878;margin-right:4px;margin-top:4px;padding:4px;'>Emerald</div>
     */
    @JvmField
    val EMERALD = FloatVector(80f / 255, 200f / 255, 120f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#0F52BA;margin-right:4px;margin-top:4px;padding:4px;'>Sapphire</div>
     */
    @JvmField
    val SAPPHIRE = FloatVector(15f / 255, 82f / 255, 186f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#FF7800;margin-right:4px;margin-top:4px;padding:4px;'>Safety
     * Orange</div>
     */
    @JvmField
    val SAFETY_ORANGE = FloatVector(232f / 255, 118f / 255, 0f, 1.0f)
    
    /**
     * <div style='color:white;background-color:#E30B5D;margin-right:4px;margin-top:4px;padding:4px;'>Raspberry</div>
     */
    @JvmField
    val RASPBERRY = FloatVector(227f / 255, 11f / 255, 924f / 255, 1.0f)
    
    /**
     * <div style='background-color:#93C572;margin-right:4px;margin-top:4px;padding:4px;'>Pistachio</div>
     */
    @JvmField
    val PISTACHIO = FloatVector(147f / 255, 197f / 255, 114f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#8E4585;margin-right:4px;margin-top:4px;padding:4px;'>Plum</div>
     */
    @JvmField
    val PLUM = FloatVector(142f / 255, 69f / 255, 133f / 255, 1.0f)
    
    /**
     * <div style='background-color:#FDDDE6;margin-right:4px;margin-top:4px;padding:4px;'>Piggy
     * Pink</div>
     */
    @JvmField
    val PIGGY_PINK = FloatVector(253f / 255, 221f / 255, 230f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#568203;margin-right:4px;margin-top:4px;padding:4px;'>Avocado</div>
     */
    @JvmField
    val AVOCADO = FloatVector(86f / 255, 130f / 255, 3f / 255, 1.0f)
    
    /**
     * <div style='background-color:#D1E231;margin-right:4px;margin-top:4px;padding:4px;'>Pear</div>
     */
    @JvmField
    val PEAR = FloatVector(209f / 255, 226f / 255, 49f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#002366;margin-right:4px;margin-top:4px;padding:4px;'>Royal
     * Blue</div>
     */
    @JvmField
    val ROYAL_BLUE = FloatVector(0f, 35f / 255, 102f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#26619C;margin-right:4px;margin-top:4px;padding:4px;'>Lapis
     * Lazuli</div>
     */
    @JvmField
    val LAPIS_LAZULI = FloatVector(38f / 255, 97f / 255, 156f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#00A86B;margin-right:4px;margin-top:4px;padding:4px;'>Jade</div>
     */
    @JvmField
    val JADE = FloatVector(0f, 168f / 255, 107f / 255, 1.0f)
    
    /**
     * <div style='background-color:#4CBB17;margin-right:4px;margin-top:4px;padding:4px;'>Kelly
     * Green</div>
     */
    @JvmField
    val KELLY_GREEN = FloatVector(76f / 255, 187f / 255, 23f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#009000;margin-right:4px;margin-top:4px;padding:4px;'>Islamic
     * Green</div>
     */
    @JvmField
    val ISLAMIC_GREEN = FloatVector(0f, 153f / 255, 0f, 1.0f)
    
    /**
     * <div style='background-color:#00FF7F;margin-right:4px;margin-top:4px;padding:4px;'>Guppie
     * Green</div>
     */
    @JvmField
    val GUPPIE_GREEN = FloatVector(0f, 1.0f, 0.5f, 1.0f)
    
    /**
     * <div style='color:white;background-color:#4B3621;margin-right:4px;margin-top:4px;padding:4px;'>Caf
     * Noir</div>
     */
    @JvmField
    val CAFE_NOIR = FloatVector(75f / 255, 54f / 255, 33f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#A67B5B;margin-right:4px;margin-top:4px;padding:4px;'>Caf
     * au Lait</div>
     */
    @JvmField
    val CAFE_AU_LAIT = FloatVector(166f / 255, 123f / 255, 91f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#6F4E37;margin-right:4px;margin-top:4px;padding:4px;'>Cofee</div>
     */
    @JvmField
    val COFFEE = FloatVector(111f / 255, 78f / 255, 55f / 255, 1.0f)
    
    /**
     * <div style='background-color:#FFA07A;margin-right:4px;margin-top:4px;padding:4px;'>Light
     * Salmon</div>
     */
    @JvmField
    val LIGHT_SALMON = FloatVector(255f / 255, 160f / 255, 122f / 255, 1.0f)
    
    /**
     * <div style='background-color:#FA8072;margin-right:4px;margin-top:4px;padding:4px;'>Salmon</div>
     */
    @JvmField
    val SALMON = FloatVector(250f / 255, 128f / 255, 114f / 255, 1.0f)
    
    /**
     * <div style='background-color:#F1921E;margin-right:4px;margin-top:4px;padding:4px;'>Mandarin</div>
     */
    @JvmField
    val MANDARIN = FloatVector(241f / 255, 146f / 255, 30f / 255, 1.0f)
    
    /**
     * <div style='background-color:#40E0D0;margin-right:4px;margin-top:4px;padding:4px;'>Turquoise</div>
     */
    @JvmField
    val TURQUOISE = FloatVector(64f / 255, 224f / 255, 208f / 255, 1.0f)
    
    /**
     * <div style='background-color:#80CC33;margin-right:4px;margin-top:4px;padding:4px;'>Yellow
     * Green</div>
     */
    @JvmField
    val YELLOW_GREEN = FloatVector(0.5f, 0.8f, 0.2f, 1.0f)
    
    /**
     * <div style='background-color:#7FFFD4;margin-right:4px;margin-top:4px;padding:4px;'>Aquamarine</div>
     */
    @JvmField
    val AQUAMARINE = FloatVector(50f / 255, 1.0f, 83f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#9F5FC8;margin-right:4px;margin-top:4px;padding:4px;'>Blue
     * Violet</div>
     */
    @JvmField
    val BLUE_VIOLET = FloatVector(0.62352f, 0.372549f, 200f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#724327;margin-right:4px;margin-top:4px;padding:4px;'>Brown</div>
     */
    @JvmField
    val BROWN = FloatVector(0.447059f, 0.264706f, 0.154706f, 1.0f)
    
    /**
     * <div style='color:white;background-color:#536872;margin-right:4px;margin-top:4px;padding:4px;'>Cadet
     * Blue</div>
     */
    @JvmField
    val CADET_BLUE = FloatVector(83f / 255, 104f / 255, 114f / 255, 1.0f)
    
    /**
     * <div style='background-color:#FEC400;margin-right:4px;margin-top:4px;padding:4px;'>Mango</div>
     */
    @JvmField
    val MANGO = FloatVector(254f / 255, 196f / 255, 0f, 1.0f)
    
    /**
     * <div style='background-color:#6495ED;margin-right:4px;margin-top:4px;padding:4px;'>Cornflower
     * Blue</div>
     */
    @JvmField
    val CORNFLOWER_BLUE = FloatVector(100f / 255, 149f / 255, 237f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#808000;margin-right:4px;margin-top:4px;padding:4px;'>Olive</div>
     */
    @JvmField
    val OLIVE = FloatVector(128f / 255, 128f / 255, 0f, 1.0f)
    
    /**
     * <div style='color:white;background-color:#556B2F;margin-right:4px;margin-top:4px;padding:4px;'>Dark
     * Olive Green</div>
     */
    @JvmField
    val DARK_OLIVE_GREEN = FloatVector(85f / 255, 107f / 255, 47f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#800080;margin-right:4px;margin-top:4px;padding:4px;'>Purple</div>
     */
    @JvmField
    val PURPLE = FloatVector(0.5f, 0f, 0.5f, 1.0f)
    
    /**
     * <div style='color:white;background-color:#228B22;margin-right:4px;margin-top:4px;padding:4px;'>Forest
     * Green</div>
     */
    @JvmField
    val FOREST_GREEN = FloatVector(34f / 255, 139f / 255, 34f / 255, 1.0f)
    
    /**
     * <div style='background-color:#FFD700;margin-right:4px;margin-top:4px;padding:4px;'>Gold</div>
     */
    @JvmField
    val GOLD = FloatVector(255f / 255, 215f / 255, 0f, 1.0f)
    
    /**
     * <div style='background-color:#C9C0BB;margin-right:4px;margin-top:4px;padding:4px;'>Silver
     * (Crayola)</div>
     */
    @JvmField
    val SILVER = FloatVector(201f / 255, 192f / 255, 187f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#CD7F32;margin-right:4px;margin-top:4px;padding:4px;'>Bronze</div>
     */
    @JvmField
    val BRONZE = FloatVector(205f / 255, 127f / 255, 50f / 255, 1.0f)
    
    /**
     * <div style='background-color:#DA8A67;margin-right:4px;margin-top:4px;padding:4px;'>Copper
     * (Crayola)</div>
     */
    @JvmField
    val COPPER = FloatVector(218f / 255, 138f / 255, 103f / 255, 1.0f)
    
    /**
     * <div style='background-color:#3EB489;margin-right:4px;margin-top:4px;padding:4px;'>Mint
     * leaf</div>
     */
    @JvmField
    val MINT_GREEN = FloatVector(62f / 255, 180f / 255, 137f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#FF00FF;margin-right:4px;margin-top:4px;padding:4px;'>Magenta</div>
     */
    @JvmField
    val MAGENTA = FloatVector(1.0f, 0f, 1.0f, 1.0f)
    
    /**
     * <div style='background-color:#2EB3FF;margin-right:4px;margin-top:4px;padding:4px;'>Light
     * Blue</div>
     */
    @JvmField
    val LIGHT_BLUE = FloatVector(46f / 255, 179f / 255, 1.0f, 1.0f)
    
    /**
     * <div style='background-color:#94E2FF;margin-right:4px;margin-top:4px;padding:4px;'>Sky
     * Blue</div>
     */
    @JvmField
    val SKY_BLUE = FloatVector(148f / 255, 226f / 255, 1.0f, 1.0f)
    /**
     * <div style='color:white;background-color:#FF6666;margin-right:4px;margin-top:4px;padding:4px;'>Red
     * Pink</div>
     */
    @JvmField
    val RED_PINK = FloatVector(1.0f, 0.4f, 0.4f, 1.0f)
    
    /**
     * <div style='background-color:#FFCBDB;margin-right:4px;margin-top:4px;padding:4px;'>Pink</div>
     */
    @JvmField
    val PINK = FloatVector(1.0f, 203f / 255, 219f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#A07100;margin-right:4px;margin-top:4px;padding:4px;'>Diarrhea
     * Brown</div>
     */
    @JvmField
    val DIARRHEA_BROWN = FloatVector(160f / 255, 113f / 255, 0f, 1.0f)
    
    /**
     * <div style='color:white;background-color:#40826D;margin-right:4px;margin-top:4px;padding:4px;'>Viridian</div>
     */
    @JvmField
    val VIRIDIAN = FloatVector(64f / 255, 130f / 255, 109f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#6C6462;margin-right:4px;margin-top:4px;padding:4px;'>Pewter</div>
     */
    @JvmField
    val PEWTER = FloatVector(108f / 255, 100f / 255, 98f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#007BA7;margin-right:4px;margin-top:4px;padding:4px;'>Cerulean</div>
     */
    @JvmField
    val CERULEAN = FloatVector(0f, 123f / 255, 167f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#E34234;margin-right:4px;margin-top:4px;padding:4px;'>Vermilion
     * (Cinnabar)</div>
     */
    @JvmField
    val VERMILLION = FloatVector(227f / 255, 66f / 255, 52f / 255, 1.0f)
    
    /**
     * <div style='background-color:#ACE1AF;margin-right:4px;margin-top:4px;padding:4px;'>Celadon</div>
     */
    @JvmField
    val CELADON = FloatVector(172f / 255, 225f / 255, 175f / 255, 1.0f)
    
    /**
     * <div style='background-color:#DCD0FF;margin-right:4px;margin-top:4px;padding:4px;'>Lavender
     * (ISCC-NBS)</div>
     */
    @JvmField
    val LAVENDER = FloatVector(220f / 255, 208f / 255, 1.0f, 1.0f)
    
    /**
     * <div style='color:white;background-color:#B57EDC;margin-right:4px;margin-top:4px;padding:4px;'>Lavender
     * (floral)</div>
     */
    @JvmField
    val LAVENDER_FLORAL = FloatVector(181f / 255, 126f / 255, 220f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#C154C1;margin-right:4px;margin-top:4px;padding:4px;'>Fuchsia
     * (Crayola)</div>
     */
    @JvmField
    val FUCHSIA_DEEP = FloatVector(193f / 255, 84f / 255, 193f / 255, 1.0f)
    
    /**
     * <div style='background-color:#F4C430;margin-right:4px;margin-top:4px;padding:4px;'>Safron</div>
     */
    @JvmField
    val SAFFRON = FloatVector(244f / 255, 196f / 255, 48f / 255, 1.0f)
    
    /**
     * <div style='background-color:#A7D1C3;margin-right:4px;margin-top:4px;padding:4px;'>Seafoam</div>
     */
    @JvmField
    val SEAFOAM = FloatVector(167f / 255, 209f / 255, 195f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#E44D2E;margin-right:4px;margin-top:4px;padding:4px;'>Cinnabar</div>
     */
    @JvmField
    val CINNABAR = FloatVector(228f / 255, 77f / 255, 46f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#4B0082;margin-right:4px;margin-top:4px;padding:4px;'>Indigo</div>
     */
    @JvmField
    val INDIGO = FloatVector(75f / 255, 0f, 130f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#5D8AA8;margin-right:4px;margin-top:4px;padding:4px;'>Air
     * Force Blue (RA)</div>
     */
    @JvmField
    val AIR_FORCE_BLUE = FloatVector(93f / 255, 138f / 255, 168f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#DC143C;margin-right:4px;margin-top:4px;padding:4px;'>Crimson</div>
     */
    @JvmField
    val CRIMSON = FloatVector(220f / 255, 20f / 255, 60f / 255, 1.0f)
    
    /**
     * <div style='background-color:#FFFDD0;margin-right:4px;margin-top:4px;padding:4px;'>Cream</div>
     */
    @JvmField
    val CREAM = FloatVector(1.0f, 253f / 255, 208f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#FF2400;margin-right:4px;margin-top:4px;padding:4px;'>Scarlet</div>
     */
    @JvmField
    val SCARLET = FloatVector(1.0f, 36f / 255, 0f, 1.0f)
    
    /**
     * <div style='background-color:#CC7722;margin-right:4px;margin-top:4px;padding:4px;'>Ochre</div>
     */
    @JvmField
    val OCHRE = FloatVector(204f / 255, 119f / 255, 34f / 255, 1.0f)
    
    /**
     * <div style='background-color:#FFE5B4;margin-right:4px;margin-top:4px;padding:4px;'>Peach</div>
     */
    @JvmField
    val PEACH = FloatVector(1.0f, 229f / 255, 180f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#9B111E;margin-right:4px;margin-top:4px;padding:4px;'>Ruby</div>
     */
    @JvmField
    val RUBY = FloatVector(155f / 255, 17f / 255, 30f / 255, 1.0f)
    
    /**
     * <div style='background-color:#E5AA70;margin-right:4px;margin-top:4px;padding:4px;'>Fawn</div>
     */
    @JvmField
    val FAWN = FloatVector(229f / 255, 170f / 255, 112f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#7B3F00;margin-right:4px;margin-top:4px;padding:4px;'>Chocolate</div>
     */
    @JvmField
    val CHOCOLATE = FloatVector(123f / 255, 63f / 255, 0f, 1.0f)
    
    /**
     * <div style='background-color:#E0B0FF;margin-right:4px;margin-top:4px;padding:4px;'>Mauve</div>
     */
    @JvmField
    val MAUVE = FloatVector(224f / 255, 176f / 255, 1.0f, 1.0f)
    
    /**
     * <div style='color:white;background-color:#FF007F;margin-right:4px;margin-top:4px;padding:4px;'>Rose</div>
     */
    @JvmField
    val ROSE = FloatVector(1.0f, 0f, 127f / 255, 1.0f)
    
    /**
     * <div style='background-color:#007FFF;margin-right:4px;margin-top:4px;padding:4px;'>Azure</div>
     */
    @JvmField
    val AZURE = FloatVector(0f, 127f / 255, 1.0f, 1.0f)
    
    /**
     * <div style='background-color:#FFF44F;margin-right:4px;margin-top:4px;padding:4px;'>Lemon
     * Yellow</div>
     */
    @JvmField
    val LEMON = FloatVector(1.0f, 244f / 255, 79f / 255, 1.0f)
    
    /**
     * <div style='background-color:#FFFACD;margin-right:4px;margin-top:4px;padding:4px;'>Lemon
     * Chifon</div>
     */
    @JvmField
    val LEMON_CHIFFON = FloatVector(1.0f, 250f / 255, 205f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#80461B;margin-right:4px;margin-top:4px;padding:4px;'>Russet</div>
     */
    @JvmField
    val RUSSET = FloatVector(128f / 255, 70f / 255, 27f / 255, 1.0f)
    
    /**
     * <div style='color:white;background-color:#000;margin-right:4px;margin-top:4px;padding:4px;'>Black</div>
     */
    @JvmField
    val BLACK = FloatVector(0f, 0f, 0f, 1.0f)
    
    /**
     * <div style='background-color:#FFF;margin-right:4px;margin-top:4px;padding:4px;'>White</div>
     */
    @JvmField
    val WHITE = FloatVector(1.0f, 1.0f, 1.0f, 1.0f)
    
    /**
     * <div style='color:white;background-color:#1E1E1E;margin-right:4px;margin-top:4px;padding:4px;'>Grey
     * 90%</div>
     */
    @JvmField
    val GREY_90 = WHITE.scalar(0.10f)

    /**
     * <div style='color:white;background-color:#333333;margin-right:4px;margin-top:4px;padding:4px;'>Grey
     * 80%</div>
     */
    @JvmField
    val GREY_80 = WHITE.scalar(0.20f)

    /**
     * <div style='color:white;background-color:#4D4D4D;margin-right:4px;margin-top:4px;padding:4px;'>Grey
     * 70%</div>
     */
    @JvmField
    val GREY_70 = WHITE.scalar(0.30f)

    /**
     * <div style='color:white;background-color:#666666;margin-right:4px;margin-top:4px;padding:4px;'>Grey
     * 60%</div>
     */
    @JvmField
    val GREY_60 = WHITE.scalar(0.4f)

    /**
     * <div style='background-color:#808080;margin-right:4px;margin-top:4px;padding:4px;'>Grey
     * 50%</div>
     */
    @JvmField
    val GREY_50 = WHITE.scalar(0.50f)

    /**
     * <div style='background-color:#878787;margin-right:4px;margin-top:4px;padding:4px;'>Grey
     * 40%</div>
     */
    @JvmField
    val GREY_40 = WHITE.scalar(0.60f)

    /**
     * <div style='background-color:#B2B2B2;margin-right:4px;margin-top:4px;padding:4px;'>Grey
     * 30%</div>
     */
    @JvmField
    val GREY_30 = WHITE.scalar(0.70f)

    /**
     * <div style='background-color:#CCCCCC;margin-right:4px;margin-top:4px;padding:4px;'>Grey
     * 20%</div>
     */
    @JvmField
    val GREY_20 = WHITE.scalar(0.80f)

    /**
     * <div style='background-color:#E6E6E6;margin-right:4px;margin-top:4px;padding:4px;'>Grey
     * 10%</div>
     */
    @JvmField
    val GREY_10 = WHITE.scalar(0.90f)

    /**
     * <div style='color:white;background-color:#F00;margin-right:4px;margin-top:4px;padding:4px;'>Red</div>
     */
    @JvmField
    val RED = FloatVector(1.0f, 0f, 0f, 1.0f)

    /**
     * <div style='background-color:#0F0;margin-right:4px;margin-top:4px;padding:4px;'>Green</div>
     */
    @JvmField
    val GREEN = FloatVector(0f, 1.0f, 0f, 1.0f)

    /**
     * <div style='color:white;background-color:#00F;margin-right:4px;margin-top:4px;padding:4px;'>Blue</div>
     */
    @JvmField
    val BLUE = FloatVector(0f, 0f, 1.0f, 1.0f)

    /**
     * <div style='background-color:#FF0;margin-right:4px;margin-top:4px;padding:4px;'>Yellow</div>
     */
    @JvmField
    val YELLOW = FloatVector(1.0f, 1.0f, 0f, 1.0f)

    /**
     * <div style='background-color:#F80;margin-right:4px;margin-top:4px;padding:4px;'>Orange</div>
     */
    @JvmField
    val ORANGE = FloatVector(1.0f, 0.5f, 0f, 1.0f)

    /**
     * <div style='background-color:#0FF;margin-right:4px;margin-top:4px;padding:4px;'>Cyan</div>
     */
    @JvmField
    val CYAN = FloatVector(0f, 1.0f, 1.0f, 1.0f)

    /**
     * <div style='background-color:#99FF00;margin-right:4px;margin-top:4px;padding:4px;'>Lime</div>
     */
    @JvmField
    val LIME = FloatVector(0.6f, 1.0f, 0f, 1.0f)

    /**
     * <div style='color:white;background-color:#080;margin-right:4px;margin-top:4px;padding:4px;'>Dark
     * Green</div>
     */
    @JvmField
    val DARK_GREEN = FloatVector(0f, 0.5f, 0f, 1.0f)

    /**
     * <div style='color:white;background-color:#008;margin-right:4px;margin-top:4px;padding:4px;'>Dark
     * Blue</div>
     */
    @JvmField
    val DARK_BLUE = FloatVector(0f, 0f, 0.5f, 1.0f)

    /**
     * <div style='color:white;background-color:#800;margin-right:4px;margin-top:4px;padding:4px;'>Dark
     * Red</div>
     */
    @JvmField
    val DARK_RED = FloatVector(0.5f, 0f, 0f, 1.0f)

    /**
     * <div style='color:white;background-color:#80F;margin-right:4px;margin-top:4px;padding:4px;'>Violet</div>
     */
    @JvmField
    val VIOLET = FloatVector(127f / 255, 0f, 1.0f, 1.0f)

    /**
     * <div style='color:white;background-color:#168876;margin-right:4px;margin-top:4px;padding:4px;'>Catan
     * Blue</div>
     */
    @JvmField
    val CATAN_BLUE = FloatVector(22f / 255, 136f / 255, 118f / 225, 1.0f)

    /**
     * Converts a colour (FloatVector) to a byte arary.
     */
    fun asByteArray(colour: FloatVector): ByteArray {
        val bytes = ByteArray(4)
        bytes[0] = (colour[0] * 255).toByte()
        bytes[1] = (colour[1] * 255).toByte()
        bytes[2] = (colour[2] * 255).toByte()
        bytes[3] = (colour[3] * 255).toByte()
        return bytes
    }

    /**
     * Generate a random colour.
     *
     * @param ran
     *          The random-object you want to utilise.
     * @param alpha
     *          The opacity of the colour (range 0-1).
     * @return A new FloatVector containing the rgba-colour.
     */
    @JvmOverloads
    fun random(ran: Random = Random(), alpha: Float = 1.0f): FloatVector {
        return FloatVector(ran.nextFloat(), ran.nextFloat(), ran.nextFloat(), alpha)
    }
}

typealias Color = Colour
