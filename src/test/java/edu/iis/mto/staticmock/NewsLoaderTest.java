package edu.iis.mto.staticmock;

import edu.iis.mto.staticmock.reader.FileNewsReader;
import edu.iis.mto.staticmock.reader.WebServiceNewsReader;
import org.codehaus.jackson.map.DeserializerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.xml.ws.WebServiceException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

import static org.junit.Assert.*;

/**
 * Created by piotr on 14.04.2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( {ConfigurationLoader.class, NewsReaderFactory.class} )
public class NewsLoaderTest {
    @Test
    public void loadNewsTest() throws Exception {
        // given
        mockStatic(ConfigurationLoader.class);
        mockStatic(NewsReaderFactory.class);
        ConfigurationLoader configurationLoader = mock(ConfigurationLoader.class);
        Configuration configuration = mock(Configuration.class);
        when(ConfigurationLoader.getInstance()).thenReturn(configurationLoader);
        when(configurationLoader.loadConfiguration()).thenReturn(configuration);
        when(configuration.getReaderType()).thenReturn("WS");
        WebServiceNewsReader webServiceNewsReader = mock(WebServiceNewsReader.class);
        when(NewsReaderFactory.getReader(eq("WS"))).thenReturn(webServiceNewsReader);
        IncomingNews incomingNews = mock(IncomingNews.class);
        when(webServiceNewsReader.read()).thenReturn(incomingNews);
        List<IncomingInfo> incomingNewsList = new ArrayList<>();
        incomingNewsList.add(new IncomingInfo("Public", SubsciptionType.NONE));
        incomingNewsList.add(new IncomingInfo("Subscription", SubsciptionType.A));
        when(incomingNews.elems()).thenReturn(incomingNewsList);

        NewsLoader newsLoader = new NewsLoader();

        // when
        PublishableNews result = newsLoader.loadNews();

        // then
        verify(webServiceNewsReader, times(1)).read();
        assertThat(result.getPublicContent().size(), is(equalTo(1)));
        assertThat(result.getSubscribentContent().size(), is(equalTo(1)));
    }
}