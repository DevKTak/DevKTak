package com.devktak.modules.navMenu;

import com.devktak.modules.navMenu.form.BodyLogForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NavMenuService {

    private static final String SAVE_PATH = "C:\\upload";

    private final NavMenuRepository navMenuRepository;

    /** BODYLOG 페이지 사진 업로드 **/
    public void bodyLogUpload(BodyLogForm bodyLogForm) {
        try {
            // 파일 정보
            String originFileName = bodyLogForm.getBodyPicture().getOriginalFilename();
            String realFileName = originFileName.substring(0, originFileName.lastIndexOf("."));
            String extension = originFileName.substring(originFileName.lastIndexOf("."));
            Long size = bodyLogForm.getBodyPicture().getSize();

            // 서버에 저장 될 파일 이름
            String saveFileName = realFileName + "_" + getNowSumExtension(extension);

            String fullPath = SAVE_PATH + File.separator + saveFileName;

            log.debug("originFileName ::: {}", originFileName);
            log.debug("realFileName ::: {}", realFileName);
            log.debug("extension ::: {}", extension);
            log.debug("size ::: {}", size);
            log.debug("saveFileName ::: {}", saveFileName);
            log.debug("fullPath ::: {}", fullPath);
            log.debug("title ::: {}", bodyLogForm.getTitle());
            log.debug("contents ::: {}", bodyLogForm.getContents());

            writeFile(bodyLogForm.getBodyPicture(), fullPath);

            BodyLog bodyLog = BodyLog.builder()
                    .title(bodyLogForm.getTitle())
                    .contents(bodyLogForm.getContents())
                    .originFileName(originFileName)
                    .saveFileName(saveFileName)
                    .extension(extension)
                    .fileSize(size)
                    .fullPath(fullPath)
                    .build();

            navMenuRepository.save(bodyLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** 현재 시간을 기준으로 파일이름 생성 **/
    private String getNowSumExtension(String extension) {
        String backFileName = "";

        Calendar calendar = Calendar.getInstance();
        backFileName += calendar.get(Calendar.YEAR);
        backFileName += calendar.get(Calendar.MONTH) + 1;
        backFileName += calendar.get(Calendar.DATE);
        backFileName += calendar.get(Calendar.HOUR);
        backFileName += calendar.get(Calendar.MINUTE);
        backFileName += calendar.get(Calendar.SECOND);
        backFileName += extension;

        return backFileName;
    }

    /** 파일 Write **/
    private void writeFile(MultipartFile multipartFile, String fullPath) throws IOException {
        byte[] data = multipartFile.getBytes();
        FileOutputStream fos = new FileOutputStream(fullPath);
        fos.write(data);
        fos.close();
    }

//    /** bodyLog 테이블 조회 **/
//    public List<BodyLog> bodyLogList() {
//        return navMenuRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//    }
}
