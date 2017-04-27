package th.agoda.data.downloader.input;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.enums.ProtoCol;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlParserTest {

	@Autowired
	private UrlParser urlParser;

	@Test
	public void testForEmptyUrl() {
		try {
			urlParser.parse("");
			Assert.fail("RuntimeException expected");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testForInvalidUrl() {
		try {
			urlParser.parse("someScheme://user:password@ftp.site.com/home/test/someFile.txt");
			Assert.fail("RuntimeException expected");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testForHttpUrl() {
		String urlString = "https://www.google.co.in/logos/doodles/2017/cassini-spacecraft-dives-between-saturn-and-its-rings-5717425520640000-law.gif";
		UrlBean urlBean = urlParser.parse(urlString);

		Assert.assertEquals(ProtoCol.HTTPS, urlBean.getProtocol());
		Assert.assertEquals("www.google.co.in", urlBean.getHostname());
		Assert.assertEquals(urlString, urlBean.getUri());
		Assert.assertNull(urlBean.getUsername());
		Assert.assertNull(urlBean.getPassword());
		Assert.assertEquals("/logos/doodles/2017/cassini-spacecraft-dives-between-saturn-and-its-rings-5717425520640000-law.gif", urlBean.getRemoteFileName());
	}

	@Test
	public void testForFtpUrl() {
		String urlString = "ftp://user:password@ftp.site.com/home/test/someFile.txt";
		UrlBean urlBean = urlParser.parse(urlString);

		Assert.assertEquals(ProtoCol.FTP, urlBean.getProtocol());
		Assert.assertEquals("ftp.site.com", urlBean.getHostname());
		Assert.assertEquals(urlString, urlBean.getUri());
		Assert.assertEquals("user", urlBean.getUsername());
		Assert.assertEquals("password", urlBean.getPassword());
		Assert.assertEquals("/home/test/someFile.txt", urlBean.getRemoteFileName());
	}

	@Test
	public void testForSftpUrl() {
		String urlString = "sftp://user:password@sftp.site.com/home/test/someFile.txt";
		UrlBean urlBean = urlParser.parse(urlString);

		Assert.assertEquals(ProtoCol.SFTP, urlBean.getProtocol());
		Assert.assertEquals("sftp.site.com", urlBean.getHostname());
		Assert.assertEquals(urlString, urlBean.getUri());
		Assert.assertEquals("user", urlBean.getUsername());
		Assert.assertEquals("password", urlBean.getPassword());
		Assert.assertEquals("/home/test/someFile.txt", urlBean.getRemoteFileName());
	}
}