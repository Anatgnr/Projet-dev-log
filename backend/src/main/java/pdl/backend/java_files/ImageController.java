package pdl.backend.java_files;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

//import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ImageController {

  @Autowired
  private ObjectMapper mapper;

  private final ImageDao imageDao;

  @Autowired
  public ImageController(ImageDao imageDao) {
    this.imageDao = imageDao;
  }

  @RequestMapping(value = "/images/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<?> getImage(@PathVariable("id") long id) throws IOException {
    // TODO

    Optional<Image> test = imageDao.retrieve(id);
    if (!test.isPresent())
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    InputStream tab = new ByteArrayInputStream(test.get().getData());
    return ResponseEntity
        .ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(new InputStreamResource(tab));
  }

  @RequestMapping(value = "/images/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteImage(@PathVariable("id") long id) {
    // TODO
    Optional<Image> img = imageDao.retrieve(id);
    if (img.isPresent()) {
      imageDao.delete(img.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(value = "/images", method = RequestMethod.POST)
  public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes) {
    // TODO
    if (file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE)) {
      Image img = null;
      try {
        img = new Image(file.getOriginalFilename(), file.getBytes());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      imageDao.create(img);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(value = "/images", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
  @ResponseBody
  public ArrayNode getImageList() {
    ArrayNode nodes = mapper.createArrayNode();
    // TODO
    List<Image> listeImage = imageDao.retrieveAll();
    for (int i = 0; i < listeImage.size(); i++) {
      // nodes.insert((int) listeImage.get(i).getId(), listeImage.get(i).getData());
      nodes.add(listeImage.get(i).getId() + " " + listeImage.get(i).getName());
    }
    return nodes;
  }

}
