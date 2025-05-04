package jungjin.picture.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jungjin.M2Application;
import jungjin.picture.domain.Picture;
import jungjin.picture.domain.PictureAdminFile;
import jungjin.picture.domain.PictureFile;
import jungjin.picture.service.PictureService;
import jungjin.user.service.UserCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping({"/picture"})
public class PictureController {
    @Autowired
    PictureService pictureService;

    @RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        UserCustom principal = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userNum = principal.getUser().getNum();
        Page<Picture> pictureList = this.pictureService.listPictureById(page, 4, userNum);
        model.addAttribute("pictureList", pictureList);
        Page<Picture> pictureAllList = this.pictureService.findByStatus(page, 2);
        model.addAttribute("pictureAllList", pictureAllList);
        return "/picture/list";
    }

    @RequestMapping(value = {"/myList"}, method = {RequestMethod.GET})
    public String myList(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        UserCustom principal = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userNum = principal.getUser().getNum();
        Page<Picture> pictureList = this.pictureService.listPictureById(page, 7, userNum);
        model.addAttribute("pictureList", pictureList);
        model.addAttribute("page", Integer.valueOf(page));
        return "/picture/myList";
    }

    @RequestMapping(value = {"/myListContents"}, method = {RequestMethod.GET})
    public String myListContents(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        UserCustom principal = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userNum = principal.getUser().getNum();
        Page<Picture> pictureList = this.pictureService.listPictureById(page, 7, userNum);
        model.addAttribute("pictureList", pictureList);
        model.addAttribute("page", Integer.valueOf(page));
        return "/picture/myListContents";
    }

    @RequestMapping(value = {"/listAll"}, method = {RequestMethod.GET})
    public String listAll(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        UserCustom principal = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userNum = principal.getUser().getNum();
        Page<Picture> pictureAllList = this.pictureService.findByStatus(page, 6);
        model.addAttribute("pictureAllList", pictureAllList);
        model.addAttribute("page", Integer.valueOf(page));
        return "/picture/listAll";
    }

    @RequestMapping(value = {"/listAllContents"}, method = {RequestMethod.GET})
    public String listAllContents(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        UserCustom principal = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userNum = principal.getUser().getNum();
        Page<Picture> pictureAllList = this.pictureService.findByStatus(page, 6);
        model.addAttribute("pictureAllList", pictureAllList);
        model.addAttribute("page", Integer.valueOf(page));
        return "/picture/listAllContents";
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.GET})
    public String register(Model model, Picture picture, @RequestParam(name = "id", required = false) Long id) {
        if (id != null)
            picture = this.pictureService.showPicture(id);
        model.addAttribute("pictureForm", picture);
        return "/picture/register";
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.POST})
    public String insert(@ModelAttribute("pictureForm") Picture picture, BindingResult bindingResult, @RequestParam("file") MultipartFile[] files, @RequestParam(value = "fileDeleteArray", required = false) Long[] fileDeleteArray, Model model) {
        UserCustom principal = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        picture.setUser(principal.getUser());
        picture.setCreateDate(LocalDateTime.now());
        Picture result = this.pictureService.savePicture(picture);
        if (files != null && files.length > 0) {
            System.out.println("files.length::" + files.length);
            for (int i = 0; i < files.length; i++) {
                try {
                    System.out.println("files[i]::" + files[i].getOriginalFilename());
                    if (true != files[i].isEmpty()) {
                        byte[] bytes = files[i].getBytes();
                        PictureFile pictureFile = new PictureFile();
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                        String fileName = String.valueOf(sdf.format(date)) + "_" + String.valueOf(result.getId()) + "_" + String.valueOf(i + 1);
                        String ext = files[i].getOriginalFilename();
                        ext = ext.substring(ext.indexOf("."));
                        BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(M2Application.UPLOAD_DIR + "/picture/" + fileName + ext)));
                        pictureFile.setName(fileName + ext);
                        pictureFile.setExt(ext);
                        pictureFile.setOriName(files[i].getOriginalFilename());
                        pictureFile.setPicture(result);
                        pictureFile.setCreateDate(result.getCreateDate());
                        this.pictureService.savePictureFile(pictureFile);
                        buffStream.write(bytes);
                        buffStream.close();
                        System.out.println("You successfully uploaded :" + files[i].getOriginalFilename());
                    }
                } catch (Exception e) {
                    System.out.println("failed :" + files[i].getOriginalFilename());
                }
            }
        }
        if (fileDeleteArray != null)
            this.pictureService.depetePictureFile(fileDeleteArray);
        return "redirect:/picture/success";
    }

    @RequestMapping(value = {"/success"}, method = {RequestMethod.GET})
    public String success() {
        return "/picture/success";
    }

    @RequestMapping(value = {"/show/{id}"}, method = {RequestMethod.GET})
    public String show(Model model, @PathVariable Long id) {
        Picture detail = this.pictureService.showPicture(id);
        model.addAttribute("pictureDetail", detail);
        return "/picture/show";
    }

    @RequestMapping({"/fileDown/{bno}"})
    private void fileDown(@PathVariable Long bno, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "type", defaultValue = "user") String type) throws Exception {
        request.setCharacterEncoding("UTF-8");
        try {
            ServletOutputStream servletOutputStream = null;
            String fileUrl = M2Application.UPLOAD_DIR + "/picture/";
            String fileName = "";
            String oriFileName = "";
            if (type.equals("admin")) {
                PictureAdminFile fileVO2 = this.pictureService.adminFileDetailService(bno);
                fileName = fileVO2.getName();
                oriFileName = fileVO2.getOriName();
                fileUrl = fileUrl + fileVO2.getPath();
            } else {
                PictureFile fileVO = this.pictureService.fileDetailService(bno);
                fileName = fileVO.getName();
                oriFileName = fileVO.getOriName();
            }
            String savePath = fileUrl;
            InputStream in = null;
            OutputStream os = null;
            File file = null;
            boolean skip = false;
            String client = "";
            try {
                file = new File(savePath, fileName);
                in = new FileInputStream(file);
            } catch (FileNotFoundException fe) {
                skip = true;
            }
            client = request.getHeader("User-Agent");
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Description", "JSP Generated Data");
            if (!skip) {
                if (client.indexOf("MSIE") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else if (client.indexOf("Trident") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else {
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(oriFileName
                            .getBytes("UTF-8"), "ISO8859_1") + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                }
                response.setHeader("Content-Length", "" + file.length());
                servletOutputStream = response.getOutputStream();
                byte[] b = new byte[(int)file.length()];
                int leng = 0;
                while ((leng = in.read(b)) > 0)
                    servletOutputStream.write(b, 0, leng);
            } else {
                response.setContentType("text/html;charset=UTF-8");
                System.out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다.');history.back();</script>");
            }
            in.close();
            servletOutputStream.close();
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    @RequestMapping({"/zipFileDown/{bno}"})
    private void fileDown(@PathVariable Long bno, @RequestParam(value = "sName", defaultValue = "") String sName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        try {
            ServletOutputStream servletOutputStream = null;
            String fileUrl = M2Application.UPLOAD_DIR + "/picture/admin/" + bno;
            String fileName = "all.zip";
            String oriFileName = "all.zip";
            if (!oriFileName.equals(""))
                oriFileName = sName + ".zip";
            File fileCheck = new File(fileUrl + "/all.zip");
            if (fileCheck.exists())
                fileCheck.delete();
            createZipFile(fileUrl, fileUrl, "all.zip");
            String savePath = fileUrl;
            InputStream in = null;
            OutputStream os = null;
            File file = null;
            boolean skip = false;
            String client = "";
            try {
                file = new File(savePath, fileName);
                in = new FileInputStream(file);
            } catch (FileNotFoundException fe) {
                skip = true;
            }
            client = request.getHeader("User-Agent");
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Description", "JSP Generated Data");
            if (!skip) {
                if (client.indexOf("MSIE") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else if (client.indexOf("Trident") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else {
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(oriFileName
                            .getBytes("UTF-8"), "ISO8859_1") + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                }
                response.setHeader("Content-Length", "" + file.length());
                servletOutputStream = response.getOutputStream();
                byte[] b = new byte[(int)file.length()];
                int leng = 0;
                while ((leng = in.read(b)) > 0)
                    servletOutputStream.write(b, 0, leng);
            } else {
                response.setContentType("text/html;charset=UTF-8");
                System.out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다.');history.back();</script>");
            }
            in.close();
            servletOutputStream.close();
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    public static void createZipFile(String path, String toPath, String fileName) {
        String _path;
        File dir = new File(path);
        String[] list = dir.list();
        if (!dir.canRead() || !dir.canWrite())
            return;
        int len = list.length;
        if (path.charAt(path.length() - 1) != '/') {
            _path = path + "/";
        } else {
            _path = path;
        }
        try {
            ZipOutputStream zip_out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(toPath + "/" + fileName), 2048));
            for (int i = 0; i < len; i++)
                zip_folder("", new File(_path + list[i]), zip_out);
            zip_out.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException" + e.getMessage());
        } finally {}
    }

    private static void zip_folder(String parent, File file, ZipOutputStream zout) throws IOException {
        byte[] data = new byte[2048];
        if (file.isFile()) {
            ZipEntry entry = new ZipEntry(parent + file.getName());
            zout.putNextEntry(entry);
            BufferedInputStream instream = new BufferedInputStream(new FileInputStream(file));
            int read;
            while ((read = instream.read(data, 0, 2048)) != -1)
                zout.write(data, 0, read);
            zout.flush();
            zout.closeEntry();
            instream.close();
        } else if (file.isDirectory()) {
            String parentString = file.getPath().replace("/upload/picture/admin/16/", "");
            parentString = parentString.substring(0, parentString.length() - file.getName().length());
            ZipEntry entry = new ZipEntry(parentString + file.getName() + "/");
            zout.putNextEntry(entry);
            String[] list = file.list();
            if (list != null) {
                int len = list.length;
                for (int i = 0; i < len; i++)
                    zip_folder(entry.getName(), new File(file.getPath() + "/" + list[i]), zout);
            }
        }
    }

    @GetMapping({"/remove/{id}"})
    @ResponseBody
    public String pictureRemove(@PathVariable Long id) {
        Picture picture = this.pictureService.deletePicture(id);
        String success = "success";
        if (picture == null)
            success = "fail";
        return success;
    }
}
