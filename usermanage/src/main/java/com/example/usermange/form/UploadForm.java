package com.example.usermange.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadForm {
    private MultipartFile fileData;

    public MultipartFile getFileData() {
        return getFileData();
    }
}
