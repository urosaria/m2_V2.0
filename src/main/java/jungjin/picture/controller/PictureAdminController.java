package jungjin.picture.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import jungjin.M2Application;
import jungjin.picture.domain.Picture;
import jungjin.picture.domain.PictureAdminFile;
import jungjin.picture.service.PictureService;
import jungjin.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"/admin/picture"})
public class PictureAdminController {
    @Autowired
    PictureService pictureService;

    @RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") int page, User user) {
        Page<Picture> pictureList = this.pictureService.listPicture(page, 10);
        model.addAttribute("pictureList", pictureList);
        model.addAttribute("page", Integer.valueOf(page));
        return "/admin/picture/list";
    }

    @RequestMapping(value = {"/show/{id}"}, method = {RequestMethod.GET})
    public String show(Model model, @PathVariable Long id) {
        Picture detail = this.pictureService.showPicture(id);
        model.addAttribute("pictureDetail", detail);
        return "/admin/picture/show";
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.GET})
    public String register(Model model, Picture picture, @RequestParam(name = "id", required = false) Long id) {
        if (id != null)
            picture = this.pictureService.showPicture(id);
        model.addAttribute("pictureForm", picture);
        return "/admin/picture/register";
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.POST})
    public String insert(@ModelAttribute("pictureForm") Picture picture, BindingResult bindingResult, Model model) {
        Picture detail = this.pictureService.showPicture(Long.valueOf(picture.getId()));
        detail.update(picture);
        Picture result = this.pictureService.savePicture(detail);
        return "redirect:/admin/picture/show/" + result.getId();
    }

    @GetMapping({"/status/{id}"})
    @ResponseBody
    public String boardReplyRemove(@PathVariable Long id, @RequestParam String process, Picture picture) {
        String success = "success";
        this.pictureService.updatePictureStatus(id, process);
        return success;
    }

    @RequestMapping(value = {"/status"}, method = {RequestMethod.POST})
    public String statusUpdate(@ModelAttribute("pictureForm") Picture picture, BindingResult bindingResult, Model model) {
        Picture detail = this.pictureService.showPicture(Long.valueOf(picture.getId()));
        detail.updateStatus(picture);
        Picture result = this.pictureService.savePicture(detail);
        return "redirect:/admin/picture/show/" + result.getId();
    }

    @RequestMapping(value = {"/upload"}, method = {RequestMethod.POST})
    public String multipleSave(@RequestParam("file") MultipartFile[] files, @RequestParam("id") Long id) {
        String msg = "";
        if (files != null && files.length > 0)
            for (int i = 0; i < files.length; i++) {
                try {
                    if (true != files[i].isEmpty()) {
                        byte[] bytes = files[i].getBytes();
                        PictureAdminFile pictureAdminFile = new PictureAdminFile();
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                        String fileName = String.valueOf(sdf.format(date)) + "_" + String.valueOf(id) + "_" + String.valueOf(i + 1);
                        String ext = files[i].getOriginalFilename();
                        ext = ext.substring(ext.indexOf("."));
                        Picture picture = this.pictureService.showPicture(id);
                        File file = new File(M2Application.UPLOAD_DIR + "/picture/admin/" + id);
                        if (!file.exists())
                            file.mkdirs();
                        BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(M2Application.UPLOAD_DIR + "/picture/admin/" + id + "/" + fileName + ext)));
                        pictureAdminFile.setName(fileName + ext);
                        pictureAdminFile.setExt(ext);
                        pictureAdminFile.setOriName(files[i].getOriginalFilename());
                        pictureAdminFile.setPicture(picture);
                        pictureAdminFile.setCreateDate(LocalDateTime.now());
                        pictureAdminFile.setPath("/admin/" + id);
                        this.pictureService.savePictureAdminFile(pictureAdminFile);
                        buffStream.write(bytes);
                        buffStream.close();
                        msg = "success";
                    }
                } catch (Exception exception) {}
            }
        return "redirect:/admin/picture/show/" + id;
    }

    @GetMapping({"/file/remove/{id}"})
    @ResponseBody
    public String pictureAdminFileRemove(@PathVariable Long id) {
        this.pictureService.deletePictureAdminFile(id);
        String success = "success";
        return success;
    }
}
