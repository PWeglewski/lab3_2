package edu.iis.mto.staticmock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.powermock.api.mockito.PowerMockito.*;

import static org.junit.Assert.*;

/**
 * Created by piotr on 14.04.2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( ConfigurationLoader.class )
public class NewsLoaderTest {
    @Test
    public void loadNewsTest() throws Exception {
        mockStatic(ConfigurationLoader.class);
    }
}