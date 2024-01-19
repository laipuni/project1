package project.project1.Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import project.project1.file.FileStore;

import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileStore fileStore;

    @ResponseBody
    @GetMapping("/images/{uploadName}")
    public Resource downloadImage(@PathVariable("uploadName") String uploadname) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(uploadname));
    }
}
