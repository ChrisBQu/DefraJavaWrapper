package source.defra;

public class DefraWrapper {
    static {
		System.loadLibrary("nativewrapper");
        System.loadLibrary("defradb");
    }

    // JNI declarations

    // Callable functions

    public DefraNode NewNode(DefraNodeInitOptions options) {
        return new DefraNode(options);
    }
    
}
