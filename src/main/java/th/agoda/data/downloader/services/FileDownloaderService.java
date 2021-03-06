package th.agoda.data.downloader.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.common.FileDownloaderFactory;
import th.agoda.data.downloader.enums.ProtoCol;

@Service
@Slf4j
public class FileDownloaderService {

	@Autowired
	private FileDownloaderFactory fileDownloaderFactory;

	@Async
	public void download(ProtoCol protoCol, UrlBean urlBean) {
		try {
			fileDownloaderFactory.get(protoCol).readFile(urlBean);
		} catch (RuntimeException e) {
			log.warn("RuntimeException caught while downloading from urlBean {}, exception message = {} ", urlBean, e.getMessage());
		} catch (Exception e) {
			log.error("Exception caught. while downloading from urlBean {}, exception message = {} "+e.getMessage());
			throw e;
		}
	}
}
