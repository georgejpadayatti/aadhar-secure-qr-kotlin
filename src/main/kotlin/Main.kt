import com.github.jaiimageio.jpeg2000.impl.J2KImageReaderSpi
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.zip.GZIPInputStream
import javax.imageio.ImageIO
import javax.imageio.ImageReader
import javax.imageio.spi.IIORegistry

/**
 * Reads JPEG2000 image using the appropriate image reader and returns the buffered image
 */
@Throws(IOException::class)
fun readJPEG2000Image(imageBytes: ByteArray?): BufferedImage? {

    val registry = IIORegistry.getDefaultInstance()
    registry.registerServiceProvider(J2KImageReaderSpi())

    val imageReaderIterator: Iterator<ImageReader> = ImageIO.getImageReadersBySuffix("jp2")
    return if (imageReaderIterator.hasNext()) {
        val imageReader: ImageReader = imageReaderIterator.next()
        val imageInputStream = ImageIO.createImageInputStream(ByteArrayInputStream(imageBytes))
        imageReader.input = imageInputStream
        imageReader.read(0)
    } else {
        //Exception
        null
    }
}

/**
 * Check if mobile is available
 *
 * from 0th index to the first 255 delimeter we get values from [0, 1, 2, 3] with following meaning
 * 0 -> email & mobile both are not available
 * 1 -> email is available but mobile isn't
 * 2 -> email isn't available but mobile is
 * 3 -> email and mobile both are available
 *
 */
fun isMobilePresent(emailMobileNumberBit: String): Boolean {
    if (listOf('2'.toString(), '3'.toString()).contains(emailMobileNumberBit)) {
        return true
    }
    return false
}

/**
 * Check if email is available
 *
 * from 0th index to the first 255 delimeter we get values from [0, 1, 2, 3] with following meaning
 * 0 -> email & mobile both are not available
 * 1 -> email is available but mobile isn't
 * 2 -> email isn't available but mobile is
 * 3 -> email and mobile both are available
 */
fun isEmailPresent(emailMobileNumberBit: String): Boolean {
    if (listOf('1'.toString(), '3'.toString()).contains(emailMobileNumberBit)) {
        return true
    }
    return false
}

/**
 * For uncompressing GZIPed Byte Array
 */
@Throws(IOException::class)
fun gzipDecompress(compressed: ByteArray?): ByteArray? {
    val bytein = ByteArrayInputStream(compressed)
    val gzin = GZIPInputStream(bytein)
    val byteout = ByteArrayOutputStream()

    var res = 0
    val buf = ByteArray(10240)
    while (res >= 0) {
        res = gzin.read(buf, 0, buf.size)
        if (res > 0) {
            byteout.write(buf, 0, res)
        }
    }

    return byteout.toByteArray()
}

fun aadharQrCodeScenario() {
    /**
     * aadharQrCodeScenario()
     *
     * Decoding aadhar QR code embedded data.
     *
     */

    // Sample data from Secure QR code specification
    val sampleData = """6979414848205548481619299442879901900893978332594614407044767717485
4072801040777146586981633254016592128309207342330475784547018105670
3201527022368291791582523470375471250488792130918178960780916888458
3848396456653007022479356336240198130363930881632367124738541517499
4944581396473788086806141692732214047414765965839531692488313762243
9633516957706481298714057814488581947919017353764497023212514225396
3784979138011318798385442436099901621998283624816070080504830712594
5257605969343415767556267915904036368781398616655993833194292283644
3418391319795873869700141049383928129869234282995156671253030975875
9364649701153639921979798429707566199261950037418171329283207372048
0149486691606667761984140406333846771047176975075217175867767090842
0036495617886363610598886726092988757709295557040780378302139789734
1999914616790441029837229129746669225095633201097644321593502503404
4407141105151670348891282589655834359650302258453485645820515213488
0074257444287708777419466898351662963107334120270545338278061377542
7336949283388084891654484225446940941660942440637784744293259916479
8414070881894629644896702318664819042373384948728130988908758456400
3437037038710879895018022086543601275248721667704181731293011974760
1017807577565413977545693375480131324240696099879479436722576566447
9395931955906845912618090380231221781720061504995691852188387493372
3828159703728892446400999753093833679817602359729232832096508699018
4531426188862965408313308973495924965144113396593829090645266653313
7745820361389820133685614747191544471348944666115605897582518290632
2637030028217582347956984726143934840455825140227373086505348221458
9180028302043821438357583302818374143973997002745047526405755760407
0450066944235013370817802998150803248403378288126443000419003568164
2911426109823019897675202600207987688279659723561501559448618205778
1476152918170746403157005216896239428521706033466061587608065036133
1530744321959521313685642341680054477701903457770249176298796391711
6171992985207826530916075926098959061815888989183529473561436667450
3961584445497685736312628248483551986529867423016255476553691922054
2416862309689752295117009281712815499026823653023336774129517888398
0686979604051223589931173433785868453115672141628011447336882646309
8485252394260075790386415875290922570568686439586036262465414002334
1178700889228016605294147597843187998438061300969981908812404041388
6929330978233530529672066622024330417508635827821135578995799801480
1209332293458940463859106591986434520433810583569309224929264228263
8414773789493293124439582159392944326694642602165340745608827230068
3845979281234025307833029113552695267520379083343023785283174060143
3198364243363569730205351077393441691141240055900819091229931605146
8655201830018102397084643225883899560362917601755588438191054182345
8023961017432363660609526272294014370606369884649967328537762118057
0537788160304936809915237889489342387891057012783726694920184573202
7896729639223800282711244480242656443966863415084478303513802421275
4239384941028383040959498850324679954444468760695488151059751568641
0993828907588979699141180160893062603338104857903239845856783130275
935413569275439908789983311663211937449259444259898972766208
"""

    // Aadhar basic fields
    val fields = listOf(
        "email_mobile_number_bit",
        "reference_id",
        "name",
        "dob",
        "gender",
        "care_of",
        "district",
        "landmark",
        "house",
        "location",
        "pincode",
        "post_office",
        "state",
        "street",
        "sub_district",
        "vtc",
    )

    // String to BigInteger
    val base10Integer = sampleData.replace("\n", "").toBigInteger()
    println(base10Integer)

    // BigInteger to ByteArray
    val base10Bytes = base10Integer.toByteArray()

    // GZIP decompression
    val delimiterIndexList: MutableList<Int> = mutableListOf()
    val decompressedBytes = gzipDecompress(base10Bytes)
    val decompressedBytesLength = decompressedBytes?.size

    // Identify delimiter index(s)
    decompressedBytes?.forEachIndexed { index, decompressedByte ->
        if (decompressedByte.toUByte().toInt() == 255) {
            delimiterIndexList.add(index)
        }
    }

    // Extract basic field data
    var previous = 0
    val zippedList = fields zip delimiterIndexList
    val extractedBasicFieldData: HashMap<String, String> = HashMap()
    zippedList.forEach { pair ->

        val str =
            decompressedBytes?.copyOfRange(previous, pair.component2())?.let { String(it, StandardCharsets.ISO_8859_1) }

        extractedBasicFieldData[pair.component1()] = str.toString()
        previous = pair.component2() + 1
    }

    // Extract photo
    val ending = decompressedBytesLength?.minus(256)
    val lengthToSubtract: Int

    val isMobilePresentVal = extractedBasicFieldData["email_mobile_number_bit"]?.let { isMobilePresent(it) }
    val isEmailPresentVal = extractedBasicFieldData["email_mobile_number_bit"]?.let { isEmailPresent(it) }

    lengthToSubtract = if (isMobilePresentVal == true && isEmailPresentVal == true) {
        32 * 2
    } else if ((isMobilePresentVal == true && isEmailPresentVal == false) || (isMobilePresentVal == false && isEmailPresentVal == true)) {
        32 * 1
    } else {
        32 * 0
    }

    val photoBytes =
        ending?.let { decompressedBytes.copyOfRange(delimiterIndexList[15] + 1, it.minus(lengthToSubtract)) }

    if (photoBytes != null) {

        val bImage = readJPEG2000Image(photoBytes)

        if (bImage != null) {
            val bos = ByteArrayOutputStream()
            ImageIO.write(bImage, "jpg", bos)
            val encodedByteArray = Base64.getEncoder().encode(bos.toByteArray())
            val encodedString = String(encodedByteArray, StandardCharsets.ISO_8859_1)
            extractedBasicFieldData["photo"] = encodedString

        }

    }

    val json = Json { prettyPrint = true }

    println("[INFO] Aadhar QR code embedded data: ${json.encodeToString(extractedBasicFieldData)}")
}


/**
 * reverseBytes()
 *
 * Reverses a bytearray (Little endian to Big endian)
 *
 */
private fun reverseBytes(b: ByteArray): ByteArray {
    var tmp: Byte
    for (i in 0 until b.size / 2) {
        tmp = b[i]
        b[i] = b[b.size - i - 1]
        b[b.size - i - 1] = tmp
    }
    return b
}

/**
 * toHex()
 *
 * Extends ByteArray to return hex representation as a string.
 *
 */
fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> " %02x ".format(eachByte) }

private fun packRaw_u64b(`val`: Long): ByteArray {
    /**
     * packRaw_u64b()
     *
     * Kotlin implementation of struct (packing format)
     *
     * Accepts long
     *
     * Returns unsigned 8 byte big endian string
     *
     */

    var `val` = `val`
    var bx = ByteArray(8)
    `val` = `val` and -0x1
    if (`val` >= 0) {
        bx[0] = (`val` and 0xff).toByte()
        bx[1] = (`val` shr 8 and 0xff).toByte()
        bx[2] = (`val` shr 16 and 0xff).toByte()
        bx[3] = (`val` shr 24 and 0xff).toByte()
        bx[4] = (`val` shr 32 and 0xff).toByte()
        bx[5] = (`val` shr 40 and 0xff).toByte()
        bx[6] = (`val` shr 48 and 0xff).toByte()
        bx[7] = (`val` shr 56 and 0xff).toByte()
    }

    bx = reverseBytes(bx)

    return bx
}
@OptIn(ExperimentalSerializationApi::class)
fun main() {

//    aadharQrCodeScenario()

    val epoch = (System.currentTimeMillis() / 1000).toLong()

    println("[INFO] Epoch time: $epoch")

    println("[INFO] Hex representation (of each byte): ${packRaw_u64b(epoch).toHex()}")

    println("[INFO] Base64 encoded packed data: ${Base64.getEncoder().encodeToString(packRaw_u64b(epoch))}")


}

