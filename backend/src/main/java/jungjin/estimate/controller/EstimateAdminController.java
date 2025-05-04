package jungjin.estimate.controller;

import jungjin.estimate.domain.Structure;
import jungjin.estimate.domain.StructureDetail;
import jungjin.estimate.service.EstimateDetailService;
import jungjin.estimate.service.EstimateService;
import jungjin.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/admin/estimate"})
public class EstimateAdminController {
    @Autowired
    EstimateService estimateService;

    @Autowired
    EstimateDetailService estimateDetailService;

    @RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "status", defaultValue = "") String status, @RequestParam(value = "searchCondition", defaultValue = "") String searchCondition, @RequestParam(value = "searchText", defaultValue = "") String searchText, User user) {
        Page<Structure> estimateList = null;
        if ((status != null && !status.equals("")) || (searchCondition != null && !searchCondition.equals(""))) {
            estimateList = this.estimateService.listEstimateStatus(searchCondition, searchText, status, page, 10);
        } else {
            estimateList = this.estimateService.listEstimateAll(page, 10);
        }
        model.addAttribute("estimateList", estimateList);
        model.addAttribute("page", Integer.valueOf(page));
        model.addAttribute("status", status);
        model.addAttribute("searchCondition", searchCondition);
        model.addAttribute("searchText", searchText);
        return "/admin/estimate/list";
    }

    @RequestMapping(value = {"/show/{id}"}, method = {RequestMethod.GET})
    public String show(Model model, @PathVariable Long id) {
        Structure structure = this.estimateService.showEstimate(id);
        StructureDetail detail = this.estimateDetailService.showEstimateDetail(id);
        model.addAttribute("structure", structure);
        model.addAttribute("structureDetail", detail);
        return "/admin/estimate/show";
    }
}
