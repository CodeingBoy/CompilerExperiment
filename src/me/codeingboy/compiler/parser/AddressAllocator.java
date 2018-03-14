package me.codeingboy.compiler.parser;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class AddressAllocator {
    private static int address = 1;

    public static String allocateAddress() {
        return "t" + address++;
    }

    public static void reset(){
        address = 1;
    }

}
