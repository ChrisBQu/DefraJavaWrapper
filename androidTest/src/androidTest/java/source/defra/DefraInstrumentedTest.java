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
        DefraNode node = new DefraNode(options);
        node.close();
    }
}
