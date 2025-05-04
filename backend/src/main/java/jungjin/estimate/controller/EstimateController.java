package jungjin.estimate.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jungjin.M2Application;
import jungjin.estimate.domain.Calculate;
import jungjin.estimate.domain.Canopy;
import jungjin.estimate.domain.Ceiling;
import jungjin.estimate.domain.Door;
import jungjin.estimate.domain.InsideWall;
import jungjin.estimate.domain.Price;
import jungjin.estimate.domain.Structure;
import jungjin.estimate.domain.StructureDetail;
import jungjin.estimate.domain.Window;
import jungjin.estimate.service.EstimateDetailService;
import jungjin.estimate.service.EstimatePriceService;
import jungjin.estimate.service.EstimateService;

import jungjin.user.service.UserCustom;
import jungjin.user.service.UserService;
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
import sk.nociar.jpacloner.JpaCloner;

@Controller
@RequestMapping({"/estimate"})
public class EstimateController {
    @Autowired
    EstimateService estimateService;

    @Autowired
    EstimateDetailService estimateDetailService;

    private static EstimatePriceService estimatePriceService;

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired(required = true)
    public void setEstimatePriceService(EstimatePriceService _estimatePriceService) {
        estimatePriceService = _estimatePriceService;
    }

    final String pateTitle = "자동판넬견적";

    @RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
    public String register(@RequestParam(value = "page", defaultValue = "1") int page, Model model, Structure structure) {
        UserCustom principal = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userNum = principal.getUser().getNum();
        Page<Structure> listEstimate = this.estimateService.listEstimate(page, 7, userNum);
        model.addAttribute("page", Integer.valueOf(page));
        model.addAttribute("listEstimate", listEstimate);
        return "/estimate/list";
    }

    @RequestMapping(value = {"/listContents"}, method = {RequestMethod.GET})
    public String listContent(@RequestParam(value = "page", defaultValue = "1") int page, Model model, Structure structure) {
        UserCustom principal = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userNum = principal.getUser().getNum();
        Page<Structure> listEstimate = this.estimateService.listEstimate(page, 7, userNum);
        model.addAttribute("page", Integer.valueOf(page));
        model.addAttribute("listEstimate", listEstimate);
        return "/estimate/listContents";
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.GET})
    public String register(Model model, @RequestParam(name = "type", required = false) String type, Structure structure, StructureDetail structureDetail, @RequestParam(name = "id", required = false) Long id) {
        if (id != null) {
            structure = this.estimateService.showEstimate(id);
            type = structure.getStructureType();
            structureDetail = this.estimateDetailService.showEstimateDetail(id);
        }
        structure.setStructureDetail(structureDetail);
        model.addAttribute("insideWallListSize", Integer.valueOf(structureDetail.getInsideWallList().size()));
        model.addAttribute("ceilingListSize", Integer.valueOf(structureDetail.getCeilingList().size()));
        model.addAttribute("doorListSize", Integer.valueOf(structureDetail.getDoorList().size()));
        model.addAttribute("downpipeListSize", Integer.valueOf(structureDetail.getDownpipeList().size()));
        model.addAttribute("canopyListSize", Integer.valueOf(structureDetail.getCanopyList().size()));
        model.addAttribute("windowListSize", Integer.valueOf(structureDetail.getWindowList().size()));
        model.addAttribute("structureForm", structure);
        model.addAttribute("type", type);
        return "/estimate/register";
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.POST})
    public String register(@ModelAttribute("structureForm") Structure structure, StructureDetail structureDetail, @RequestParam(name = "mode", defaultValue = "") String mode, Model model) throws IOException {
        UserCustom principal = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (mode.equals(""))
            structure.setUser(principal.getUser());
        Structure result = this.estimateService.saveEstimate(structure);
        if (result != null) {
            structureDetail = structure.getStructureDetail();
            structureDetail.setStructure(result);
            if (structureDetail.getInsideWallYn().equals("N")) {
                structureDetail.setInsideWallType("");
                structureDetail.setInsideWallPaper("");
                structureDetail.setInsideWallThick(0);
            }
            if (structureDetail.getCeilingYn().equals("N")) {
                structureDetail.setCeilingType("");
                structureDetail.setCeilingPaper("");
                structureDetail.setCeilingThick(0);
            }
            this.estimateDetailService.saveEstimateDetail(structureDetail);
            this.estimateDetailService.saveEstimateDetailEtc(structureDetail);
            List<String> calList = EstimateCalculate.mainCal(structureDetail);
            if (calList.size() > 0) {
                this.estimateService.deleteCal(Long.valueOf(result.getId()));
                int index = 0;
                for (String value : calList) {
                    Calculate calculate = new Calculate();
                    String[] calculateArray = value.split("\\|");
                    calculate.setName(calculateArray[0]);
                    calculate.setStandard(calculateArray[1]);
                    calculate.setUnit(calculateArray[2]);
                    calculate.setAmount((int)Double.parseDouble(calculateArray[3]));
                    calculate.setTotal(Long.valueOf(calculateArray[4]).longValue());
                    calculate.setEPrice(Integer.parseInt(calculateArray[5]));
                    calculate.setUPrice(Integer.parseInt(calculateArray[6]));
                    calculate.setStructure(result);
                    calculate.setSort(index++);
                    this.estimateService.saveCal(calculate);
                }
                if (structureDetail.getDoorYn().equals("Y")) {
                    int doorListCount = structureDetail.getDoorList().size();
                    for (int i = 0; i < doorListCount; i++) {
                        Calculate calculate = new Calculate();
                        int w = ((Door)structureDetail.getDoorList().get(i)).getWidth();
                        int h = ((Door)structureDetail.getDoorList().get(i)).getHeight();
                        int e = ((Door)structureDetail.getDoorList().get(i)).getAmount();
                        String st = ((Door)structureDetail.getDoorList().get(i)).getSubType();
                        String standard = "";
                        Price price = new Price();
                        String thick = String.valueOf(structureDetail.getOutsideWallThick());
                        if (st.equals("S")) {
                            price = estimatePriceService.showPrice("D", "S", thick + "티판넬용", String.valueOf(w) + "*" + String.valueOf(h));
                            standard = "스윙도어";
                        } else if (st.equals("F")) {
                            price = estimatePriceService.showPrice("D", "F", thick + "티판넬용", String.valueOf(w) + "*" + String.valueOf(h));
                            standard = "방화문";
                        } else if (st.equals("H")) {
                            price = estimatePriceService.showPrice("D", "H", "마감"+ thick + "티", "");
                            standard = "행거도어(EPS전용)";
                        }
                        int startPrice = 0, gapPrice = 0, maxPrice = 0, ePrice = 0, uPrice = 0;
                        long total = 0L;
                        if (price != null) {
                            startPrice = price.getStartPrice();
                            total = (startPrice * e);
                            uPrice = startPrice;
                        }
                        calculate.setName("도어");
                        calculate.setStandard(String.valueOf(w) + "*" + String.valueOf(h) + "," + standard);
                        calculate.setUnit("EA");
                        calculate.setAmount(e);
                        calculate.setTotal(total);
                        calculate.setStructure(result);
                        calculate.setType("D");
                        calculate.setEPrice(0);
                        calculate.setUPrice(uPrice);
                        calculate.setSort(index++);
                        this.estimateService.saveCal(calculate);
                    }
                }
                if (structureDetail.getWindowYn().equals("Y")) {
                    int windowListCount = structureDetail.getWindowList().size();
                    for (int i = 0; i < windowListCount; i++) {
                        Calculate calculate = new Calculate();
                        int w = ((Window)structureDetail.getWindowList().get(i)).getWidth();
                        int h = ((Window)structureDetail.getWindowList().get(i)).getHeight();
                        int e = ((Window)structureDetail.getWindowList().get(i)).getAmount();
                        String type = ((Window)structureDetail.getWindowList().get(i)).getType();
                        Price price = new Price();
                        String thick = String.valueOf(structureDetail.getOutsideWallThick());
                        if (type.equals("S")) {
                            price = estimatePriceService.showPrice("W", "S", thick + "티판넬용", "16mm유리");
                        } else if (type.equals("D")) {
                            price = estimatePriceService.showPrice("W", "D", "225T", "16mm유리");
                        }
                        int startPrice = 0, gapPrice = 0, maxPrice = 0, uPrice = 0;
                        long total = 0L;
                        if (price != null) {
                            startPrice = price.getStartPrice();
                            total = (startPrice * e);
                            uPrice = startPrice;
                        }
                        calculate.setName("창호");
                        calculate.setStandard(String.valueOf(w) + "*" + String.valueOf(h));
                        calculate.setUnit("EA");
                        calculate.setAmount(e);
                        calculate.setTotal(total);
                        calculate.setStructure(result);
                        calculate.setType("D");
                        calculate.setEPrice(0);
                        calculate.setUPrice(uPrice);
                        calculate.setSort(index++);
                        this.estimateService.saveCal(calculate);
                    }
                }
                this.estimateService.excel("", Long.valueOf(result.getId()));
            }
        }
        return "redirect:/estimate/calculate?id=" + result.getId();
    }

    @RequestMapping(value = {"/calculate"}, method = {RequestMethod.GET})
    public String calculate(Model model, StructureDetail structureDetail, @RequestParam(name = "mode", defaultValue = "") String mode, @RequestParam(name = "id", required = false) Long id) {
        if (id != null) {
            StructureDetail detail = this.estimateDetailService.showEstimateDetailId(id);
            if (detail != null) {
                int canopyCount = detail.getCanopyList().size();
                int ceilingCount = detail.getCeilingList().size();
                int doorCount = detail.getDoorList().size();
                int insideWallCount = detail.getInsideWallList().size();
                int windowCont = detail.getWindowList().size();
                List<Calculate> doorPrice = this.estimateService.findByStructureIdAndType(Long.valueOf(detail.getStructure().getId()));
                if (canopyCount > 0) {
                    int canopyTotal = 0;
                    for (int i = 0; i < canopyCount; i++)
                        canopyTotal += ((Canopy)detail.getCanopyList().get(i)).getAmount();
                    model.addAttribute("canopyTotal", Integer.valueOf(canopyTotal));
                }
                if (ceilingCount > 0) {
                    int ceilingTotal = 0;
                    for (int i = 0; i < ceilingCount; i++)
                        ceilingTotal += ((Ceiling)detail.getCeilingList().get(i)).getAmount();
                    model.addAttribute("ceilingTotal", Integer.valueOf(ceilingTotal));
                }
                if (doorCount > 0) {
                    int doorTotal = 0;
                    for (int i = 0; i < doorCount; i++)
                        doorTotal += ((Door)detail.getDoorList().get(i)).getAmount();
                    model.addAttribute("doorTotal", Integer.valueOf(doorTotal));
                }
                if (insideWallCount > 0) {
                    int insideWallTotal = 0;
                    for (int i = 0; i < insideWallCount; i++)
                        insideWallTotal += ((InsideWall)detail.getInsideWallList().get(i)).getAmount();
                    model.addAttribute("insideWallTotal", Integer.valueOf(insideWallTotal));
                }
                if (windowCont > 0) {
                    int windowTotal = 0;
                    for (int i = 0; i < windowCont; i++)
                        windowTotal += ((Window)detail.getWindowList().get(i)).getAmount();
                    model.addAttribute("windowTotal", Integer.valueOf(windowTotal));
                }
                structureDetail = detail;
                model.addAttribute("doorPriceCount", Integer.valueOf(doorPrice.size()));
            }
        }
        model.addAttribute("structureDetail", structureDetail);
        model.addAttribute("mode", mode);
        return "/estimate/calculate";
    }

    @GetMapping({"/remove/{id}"})
    @ResponseBody
    public String structureRemove(@PathVariable Long id) {
        String success = "success";
        try {
            this.estimateService.updateStructureStatus(id, "D");
        } catch (Exception e) {
            success = "fail";
        }
        return success;
    }

    @GetMapping({"/remove/{id}/{state}"})
    @ResponseBody
    public String structureUpdateState(@PathVariable Long id, @PathVariable String state) {
        String success = "success";
        try {
            this.estimateService.updateStructureStatus(id, state);
        } catch (Exception e) {
            success = "fail";
        }
        return success;
    }

    @GetMapping({"/copy/{id}"})
    @ResponseBody
    public String structureCopy(@PathVariable Long id) {
        String success = "success";
        try {
            Structure structure = this.estimateService.showEstimateCopy(id);
            structure.setId(0L);
            structure.setPlaceName("[복사본]"+ structure.getPlaceName());
            structure.setCalculateList(null);
            Structure result = this.estimateService.saveEstimate(structure);
            if (result != null) {
                List<Calculate> calList = this.estimateService.listCal(id);
                calList = JpaCloner.clone(calList, new String[] { "*" });
                for (int i = 0; i < calList.size(); i++) {
                    Calculate calculate = new Calculate();
                    calculate = calList.get(i);
                    calculate.setStructure(result);
                    calculate.setId(0L);
                    this.estimateService.saveCal(calculate);
                }
                StructureDetail structureDetail = this.estimateDetailService.showEstimateDetailCopy(id);
                Long structureDetilIdOld = structureDetail.getId();
                structureDetail.setId(Long.valueOf(0L));
                structureDetail.setStructure(result);
                structureDetail.setCanopyList(null);
                StructureDetail detailResult = this.estimateDetailService.saveEstimateDetail(structureDetail);
                this.estimateDetailService.copyEstimateDetailEtc(structureDetilIdOld, detailResult);
                this.estimateService.excelCopy(id, Long.valueOf(result.getId()));
            }
        } catch (Exception e) {
            success = "fail";
            System.out.println(e);
        }
        return success;
    }

    @RequestMapping({"/fileDown/{bno}"})
    private void fileDown(@PathVariable Long bno, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "type", defaultValue = "user") String type) throws Exception {
        request.setCharacterEncoding("UTF-8");
        try {
            ServletOutputStream servletOutputStream = null;
            String fileUrl = M2Application.UPLOAD_DIR + "/estimate/";
            String fileName = "estimate" + bno + ".xlsx";
            String oriFileName = fileName;
            Structure structure = this.estimateService.showEstimate(bno);
            oriFileName = structure.getPlaceName() + ".xlsx";
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
                System.out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
            }
            in.close();
            servletOutputStream.close();
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    @RequestMapping(value = {"/step01"}, method = {RequestMethod.GET})
    public String estimateStep01(Model model, @RequestParam(name = "type", required = false) String type, Structure structure, @RequestParam(name = "id", required = false) Long id) {
        if (id != null) {
            structure = this.estimateService.showEstimate(id);
            type = structure.getStructureType();
            StructureDetail structureDetail = this.estimateDetailService.showEstimateDetail(id);
            model.addAttribute("structureDetail", structureDetail);
        }
        model.addAttribute("structureForm", structure);
        model.addAttribute("type", type);
        return "/estimate/step01";
    }

    @RequestMapping(value = {"/step01"}, method = {RequestMethod.POST})
    public String estimateStep01(@ModelAttribute("structureForm") Structure structure, StructureDetail structureDetail, @RequestParam(value = "returnUrl", defaultValue = "") String returnUrl, BindingResult bindingResult, Model model) {
        UserCustom principal = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        structure.setUser(principal.getUser());
        Structure result = this.estimateService.saveEstimate(structure);
        if (result != null) {
            structureDetail = this.estimateDetailService.showEstimateDetail(Long.valueOf(result.getId()));
            if (structureDetail == null) {
                structureDetail = new StructureDetail();
                structureDetail.setStructure(result);
                structureDetail = this.estimateDetailService.saveEstimateDetail(structureDetail);
            }
        }
        if (returnUrl.equals(""))
            returnUrl = "/estimate/step02?structure_detail_id=" + structureDetail.getId();
        return "redirect:" + returnUrl;
    }

    @RequestMapping(value = {"/step02"}, method = {RequestMethod.GET})
    public String estimateStep02(Model model, StructureDetail structureDetail, @RequestParam(name = "structure_detail_id", required = false) Long structure_detail_id) {
        if (structure_detail_id != null) {
            StructureDetail detail = this.estimateDetailService.showEstimateDetailId(structure_detail_id);
            if (detail != null)
                structureDetail = detail;
        }
        model.addAttribute("structureDetailForm", structureDetail);
        return "/estimate/step02";
    }

    @RequestMapping(value = {"/step02"}, method = {RequestMethod.POST})
    public String estimateStep02(@ModelAttribute("structureDetailForm") StructureDetail structureDetail, @RequestParam(value = "returnUrl", defaultValue = "") String returnUrl, BindingResult bindingResult, Model model) {
        StructureDetail detail = this.estimateDetailService.showEstimateDetailId(structureDetail.getId());
        detail.updateStep2(structureDetail);
        StructureDetail result = this.estimateDetailService.saveEstimateDetail(detail);
        if (returnUrl.equals(""))
            returnUrl = "/estimate/step03?structure_detail_id=" + result.getId();
        return "redirect:" + returnUrl;
    }

    @RequestMapping(value = {"/step03"}, method = {RequestMethod.GET})
    public String estimateStep03(Model model, InsideWall insideWall, StructureDetail structureDetail, @RequestParam(name = "structure_detail_id", required = false) Long structure_detail_id) {
        if (structure_detail_id != null) {
            StructureDetail detail = this.estimateDetailService.showEstimateDetailId(structure_detail_id);
            if (detail != null)
                structureDetail = detail;
        }
        model.addAttribute("insideWallListSize", Integer.valueOf(structureDetail.getInsideWallList().size()));
        model.addAttribute("ceilingListSize", Integer.valueOf(structureDetail.getCeilingList().size()));
        model.addAttribute("doorListSize", Integer.valueOf(structureDetail.getDoorList().size()));
        model.addAttribute("downpipeListSize", Integer.valueOf(structureDetail.getDownpipeList().size()));
        model.addAttribute("canopyListSize", Integer.valueOf(structureDetail.getCanopyList().size()));
        model.addAttribute("windowListSize", Integer.valueOf(structureDetail.getWindowList().size()));
        model.addAttribute("structureDetailForm", structureDetail);
        return "/estimate/step03";
    }

    @RequestMapping(value = {"/step03"}, method = {RequestMethod.POST})
    public String estimateStep03(@ModelAttribute("structureDetailForm") StructureDetail structureDetail, @RequestParam(value = "returnUrl", defaultValue = "") String returnUrl, BindingResult bindingResult, Model model) {
        StructureDetail detail = this.estimateDetailService.showEstimateDetailId(structureDetail.getId());
        detail.updateGucci(structureDetail);
        this.estimateDetailService.saveEstimateDetail(detail);
        StructureDetail result = this.estimateDetailService.saveEstimateDetailEtc(structureDetail);
        model.addAttribute("result", result);
        if (returnUrl.equals(""))
            returnUrl = "/estimate/step04?structure_detail_id=" + result.getId();
        return "redirect:" + returnUrl;
    }

    @RequestMapping(value = {"/step04"}, method = {RequestMethod.GET})
    public String estimateStep04(Model model, StructureDetail structureDetail, @RequestParam(name = "structure_detail_id", required = false) Long structure_detail_id) {
        if (structure_detail_id != null) {
            StructureDetail detail = this.estimateDetailService.showEstimateDetailId(structure_detail_id);
            if (detail != null)
                structureDetail = detail;
        }
        model.addAttribute("structureDetailForm", structureDetail);
        return "/estimate/step04";
    }

    @RequestMapping(value = {"/step04"}, method = {RequestMethod.POST})
    public String estimateStep04(@ModelAttribute("structureDetailForm") StructureDetail structureDetail, @RequestParam(value = "returnUrl", defaultValue = "") String returnUrl, BindingResult bindingResult, Model model) {
        StructureDetail result = this.estimateDetailService.showEstimateDetailId(structureDetail.getId());
        result.updateStep4(structureDetail);
        result = this.estimateDetailService.saveEstimateDetail(result);
        List<String> calList = EstimateCalculate.mainCal(result);
        if (calList.size() > 0) {
            this.estimateService.deleteCal(Long.valueOf(result.getStructure().getId()));
            for (String value : calList) {
                Calculate calculate = new Calculate();
                String[] calculateArray = value.split("\\|");
                calculate.setName(calculateArray[0]);
                calculate.setStandard(calculateArray[1]);
                calculate.setUnit(calculateArray[2]);
                calculate.setAmount((int)Double.parseDouble(calculateArray[3]));
                calculate.setTotal(Integer.parseInt(calculateArray[4]));
                calculate.setStructure(result.getStructure());
                this.estimateService.saveCal(calculate);
            }
            int doorListCount = result.getDoorList().size();
            for (int i = 0; i < doorListCount; i++) {
                Calculate calculate = new Calculate();
                int w = ((Door)result.getDoorList().get(i)).getWidth();
                int h = ((Door)result.getDoorList().get(i)).getHeight();
                int e = ((Door)result.getDoorList().get(i)).getAmount();
                String st = ((Door)result.getDoorList().get(i)).getSubType();
                String standard = "";
                if (st.equals("S")) {
                    standard = "스윙도어";
                } else if (st.equals("F")) {
                    standard = "방화문";
                } else if (st.equals("H")) {
                    standard = "행거도어(EPS전용)";
                }
                calculate.setName("도어");
                calculate.setStandard(String.valueOf(w) + "*" + String.valueOf(h) + "," + standard);
                calculate.setUnit("EA");
                calculate.setAmount(e);
                calculate.setTotal(0L);
                calculate.setStructure(result.getStructure());
                calculate.setType("D");
                this.estimateService.saveCal(calculate);
            }
            int windowListCount = result.getWindowList().size();
            for (int j = 0; j < windowListCount; j++) {
                Calculate calculate = new Calculate();
                int w = ((Window)result.getWindowList().get(j)).getWidth();
                int h = ((Window)result.getWindowList().get(j)).getHeight();
                int e = ((Window)result.getWindowList().get(j)).getAmount();
                calculate.setName("창호");
                calculate.setStandard(String.valueOf(w) + "*" + String.valueOf(h));
                calculate.setUnit("EA");
                calculate.setAmount(e);
                calculate.setTotal(0L);
                calculate.setStructure(result.getStructure());
                calculate.setType("D");
                this.estimateService.saveCal(calculate);
            }
        }
        model.addAttribute("result", result);
        if (returnUrl.equals(""))
            returnUrl = "/estimate/step05?structure_detail_id=" + result.getId();
        return "redirect:" + returnUrl;
    }

    @RequestMapping(value = {"/step05"}, method = {RequestMethod.GET})
    public String estimateStep05(Model model, StructureDetail structureDetail, @RequestParam(name = "structure_detail_id", required = false) Long structure_detail_id) {
        if (structure_detail_id != null) {
            StructureDetail detail = this.estimateDetailService.showEstimateDetailId(structure_detail_id);
            if (detail != null) {
                int canopyCount = detail.getCanopyList().size();
                int ceilingCount = detail.getCeilingList().size();
                int doorCount = detail.getDoorList().size();
                int insideWallCount = detail.getInsideWallList().size();
                int windowCont = detail.getWindowList().size();
                List<Calculate> doorPrice = this.estimateService.findByStructureIdAndType(Long.valueOf(detail.getStructure().getId()));
                if (canopyCount > 0) {
                    int canopyTotal = 0;
                    for (int i = 0; i < canopyCount; i++)
                        canopyTotal += ((Canopy)detail.getCanopyList().get(i)).getAmount();
                    model.addAttribute("canopyTotal", Integer.valueOf(canopyTotal));
                }
                if (ceilingCount > 0) {
                    int ceilingTotal = 0;
                    for (int i = 0; i < ceilingCount; i++)
                        ceilingTotal += ((Ceiling)detail.getCeilingList().get(i)).getAmount();
                    model.addAttribute("ceilingTotal", Integer.valueOf(ceilingTotal));
                }
                if (doorCount > 0) {
                    int doorTotal = 0;
                    for (int i = 0; i < doorCount; i++)
                        doorTotal += ((Door)detail.getDoorList().get(i)).getAmount();
                    model.addAttribute("doorTotal", Integer.valueOf(doorTotal));
                }
                if (insideWallCount > 0) {
                    int insideWallTotal = 0;
                    for (int i = 0; i < insideWallCount; i++)
                        insideWallTotal += ((InsideWall)detail.getInsideWallList().get(i)).getAmount();
                    model.addAttribute("insideWallTotal", Integer.valueOf(insideWallTotal));
                }
                if (windowCont > 0) {
                    int windowTotal = 0;
                    for (int i = 0; i < windowCont; i++)
                        windowTotal += ((Window)detail.getWindowList().get(i)).getAmount();
                    model.addAttribute("windowTotal", Integer.valueOf(windowTotal));
                }
                structureDetail = detail;
                model.addAttribute("doorPriceCount", Integer.valueOf(doorPrice.size()));
            }
        }
        model.addAttribute("structureDetail", structureDetail);
        return "/estimate/step05";
    }

    @GetMapping({"/remove/insideWall/{id}"})
    @ResponseBody
    public void insideWallRemove(@PathVariable Long id) {
        this.estimateDetailService.deleteInsideWall(id);
    }

    @GetMapping({"/remove/ceiling/{id}"})
    @ResponseBody
    public void ceilingRemove(@PathVariable Long id) {
        this.estimateDetailService.deleteCeiling(id);
    }

    @GetMapping({"/remove/window/{id}"})
    @ResponseBody
    public void windowRemove(@PathVariable Long id) {
        this.estimateDetailService.deleteWindow(id);
    }

    @GetMapping({"/remove/door/{id}"})
    @ResponseBody
    public void doorRemove(@PathVariable Long id) {
        this.estimateDetailService.deleteDoor(id);
    }

    @GetMapping({"/remove/canopy/{id}"})
    @ResponseBody
    public void canopyRemove(@PathVariable Long id) {
        this.estimateDetailService.deleteCanopy(id);
    }
}
