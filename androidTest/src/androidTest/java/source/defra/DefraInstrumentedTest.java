package source.defra;

import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class DefraInstrumentedTest {

    @Test
    public void createAndCloseNode() throws DefraException {
        DefraNodeInitOptions options = new DefraNodeInitOptions();
        options.inMemory = true;
        System.out.println("Creating DefraNode...");
        DefraNode node = new DefraNode(options);
        System.out.println("DefraNode created successfully.");
        node.close();
        System.out.println("DefraNode closed successfully.");
        System.out.println("Test passed.");
    }
}
