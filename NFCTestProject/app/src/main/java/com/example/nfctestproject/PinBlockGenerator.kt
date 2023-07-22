package com.example.nfctestproject

class PinBlockGenerator {
    companion object {

        // https://www.eftlab.com/knowledge-base/complete-list-of-pin-blocks
        // ISO 9564-1: 2002 Format 3. Format 3 is the same as format 0, except that the “fill” digits are random values from 10 to 15, and the first nibble (which identifies the block format) has the value 3.

        fun generateIso3PinBlock(pin: String, pan: String): String {
            // Prefix: 3
            // Pin length: 4
            // Pin example :  1234
            // Final result example : 341234FFFFFFFFFFF"
            val pinBlock = "3" + pin.length + pin + "F".repeat(14 - pin.length)

            // Prefix: 0000
            // 12 rightmost values example: 111122223333 from input value 1111222233334444
            // Final result example : 0000111122223333
            val panBlock = "0000" + pan.takeLast(13).dropLast(1)

            // Debugging only
            print("NFCTestProject :: Pin :: $pinBlock")
            print("NFCTestProject :: Pan :: $panBlock")
            return xorHex(pinBlock, panBlock)
        }

        //  Taking a digit from two hexadecimal numbers, converting those digits to integers, performing a bitwise XOR operation on them, and storing the result in a variable.
        fun xorHex(a: String, b: String): String {
            val result = StringBuilder()
            for (i in a.indices) {
                val xor = Integer.parseInt(a[i].toString(), 16) xor Integer.parseInt(b[i].toString(), 16)
                result.append(Integer.toHexString(xor))
            }
            return result.toString().uppercase()
        }
    }
}