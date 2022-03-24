package jungjin.estimate.controller;

import java.util.ArrayList;
import java.util.List;
import jungjin.estimate.domain.Canopy;
import jungjin.estimate.domain.Ceiling;
import jungjin.estimate.domain.Door;
import jungjin.estimate.domain.InsideWall;
import jungjin.estimate.domain.Price;
import jungjin.estimate.domain.StructureDetail;
import jungjin.estimate.domain.Window;
import jungjin.estimate.service.EstimatePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstimateCalculate {
    static long _OA = 0L, _NA = 0L, _RA = 0L, _CA = 0L, _CNA = 0L, _DA = 0L, _WA = 0L, _PA = 0L, _AA = 0L, _KA = 0L, _EC = 0L;

    static long _OAS_OC = 0L, _OAS_CB = 0L, _OAS_DH = 0L;

    static long _RAS_RT = 0L, _RAS_RB = 0L, _RAS_RH = 0L, _RAS_C = 0L, _RAS_B = 0L, _RAS_B2 = 0L, _RAS_W = 0L, _RAS_WV = 0L, _RAS_W2 = 0L, _RAS_WB = 0L, _RAS_S = 0L, _RAS_RLS = 0L, _RAS_SW = 0L, _RAS_RE = 0L, _RAS_RL = 0L, _RAS_RL2 = 0L, _RAS_WC = 0L, _RAS_EC = 0L;

    static long _DAS_W = 0L;

    static long _NAS_UB = 0L;

    static long _CNAS_S1 = 0L, _CNAS_S2 = 0L, _CNAS_S3 = 0L, _CNAS_SW = 0L;

    static long _EAS_S1 = 0L, _EAS_S2 = 0L, _EAS_S3 = 0L;

    static long _EAS_S1_UC = 0L, _EAS_S2_UC = 0L, _EAS_S3_UC = 0L;

    static long _a = 0L;

    static long _b = 0L;

    static long _c = 0L;

    static long _d = 0L;

    static int sh = 0;

    static int st = 0;

    static int sw = 0;

    static int si = 0;

    static int oomt = 0;

    static int roomt = 0;

    static String oomk = "";

    static int sb = 0;

    static int sc = 0;

    static int ge = 0;

    static int gei = 0;

    static int gd = 0;

    static int gdi = 0;

    static int swi = 0;

    static int sii = 0;

    static long swrh = 0L;

    static long swrhi = 0L;

    static long sirh = 0L;

    static long sirhi = 0L;

    static long swt = 0L;

    static long sit = 0L;

    static long sir = 0L;

    static long swr = 0L;

    static long stw = 0L;

    static long sti = 0L;

    static long sth = 0L;

    static long sg = 0L;

    private static EstimatePriceService estimatePriceService;

    @Autowired(required = true)
    public void setEstimatePriceService(EstimatePriceService _estimatePriceService) {
        estimatePriceService = _estimatePriceService;
    }

    public static List<String> mainCal(StructureDetail structureDetail) {
        _AA = 0L;
        _OA = 0L;
        _NA = 0L;
        _RA = 0L;
        _CA = 0L;
        _CNA = 0L;
        _DA = 0L;
        _WA = 0L;
        _PA = 0L;
        _EC = 0L;
        _OAS_OC = 0L;
        _OAS_CB = 0L;
        _OAS_DH = 0L;
        _RAS_RT = 0L;
        _RAS_RB = 0L;
        _RAS_RH = 0L;
        _RAS_C = 0L;
        _RAS_B = 0L;
        _RAS_B2 = 0L;
        _RAS_W = 0L;
        _RAS_W2 = 0L;
        _RAS_WB = 0L;
        _RAS_S = 0L;
        _RAS_RLS = 0L;
        _RAS_SW = 0L;
        _RAS_RE = 0L;
        _RAS_RL = 0L;
        _RAS_RL2 = 0L;
        _RAS_WC = 0L;
        _RAS_EC = 0L;
        _RAS_WV = 0L;
        _DAS_W = 0L;
        _NAS_UB = 0L;
        _CNAS_S1 = 0L;
        _CNAS_S2 = 0L;
        _CNAS_S3 = 0L;
        _CNAS_SW = 0L;
        _EAS_S1 = 0L;
        _EAS_S2 = 0L;
        _EAS_S3 = 0L;
        _KA = 0L;
        _a = 0L;
        _b = 0L;
        _c = 0L;
        _d = 0L;
        sh = structureDetail.getStructure().getHeight();
        st = structureDetail.getStructure().getTrussHeight();
        sw = structureDetail.getStructure().getWidth();
        si = structureDetail.getStructure().getLength();
        sb = structureDetail.getStructure().getRearTrussHeight();
        sc = structureDetail.getStructure().getEavesLength();
        oomt = structureDetail.getOutsideWallThick();
        roomt = structureDetail.getRoofThick();
        oomk = structureDetail.getOutsideWallType();
        gd = structureDetail.getGucci();
        ge = structureDetail.getGucciAmount();
        swi = structureDetail.getStructure().getInsideWidth();
        sii = structureDetail.getStructure().getInsideLength();
        sg = structureDetail.getStructure().getRooftopSideHeight();
        stw = structureDetail.getStructure().getRooftopWidth();
        sti = structureDetail.getStructure().getRooftopLength();
        sth = structureDetail.getStructure().getRooftopHeight();
        gdi = structureDetail.getGucciInside();
        gei = structureDetail.getGucciInsideAmount();
        String type = structureDetail.getStructure().getStructureType();
        List<Door> doorList = structureDetail.getDoorList();
        _daCal(doorList);
        List<Window> windowList = structureDetail.getWindowList();
        _waCal(windowList);
        List<InsideWall> insideWallList = structureDetail.getInsideWallList();
        List<Ceiling> ceilingList = structureDetail.getCeilingList();
        List<Canopy> canopyList = structureDetail.getCanopyList();
        _naCal(insideWallList);
        _caCal(ceilingList);
        _cnaCal(canopyList);
        _rasCal(type);
        _rasCalWc(type);
        _kaCal(type);
        cal(type);
        _etcCal();
        List<String> calArray = new ArrayList<>();
        _RA = (long)Math.ceil(_RA / 1000000.0D);
        if (_RA != 0L) {
            String roofType = structureDetail.getRoofType();
            String roofPaper = structureDetail.getRoofPaper();
            int roofThick = structureDetail.getRoofThick();
            String standard = _pStandard(roofType, roofPaper) + String.valueOf(roofThick);
            Price price = estimatePriceService.showPrice("P", "R", roofType, roofPaper);
            int startPrice = 0, gapPrice = 0, maxPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                gapPrice = price.getGapPrice();
                maxPrice = price.getMaxThickPrice();
                if (roofThick == 260) {
                    total = (startPrice + maxPrice) * _RA;
                    uPrice = startPrice + maxPrice;
                } else {
                    total = (startPrice + gapPrice * (roofThick - 50) / 25) * _RA;
                    uPrice = startPrice + gapPrice * (roofThick - 50) / 25;
                }
                ePrice = price.getEPrice();
            }
            calArray.add("지붕판넬|"+ standard + "|M²|"+ String.valueOf(_RA) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println(standard + "|M²|"+ _RA + "|" + ":지붕판넬 SAVE");
        }
        _OA = (long)Math.ceil(_OA / 1000000.0D);
        if (_OA != 0L) {
            String outsideWallType = structureDetail.getOutsideWallType();
            String outsideWallPaper = structureDetail.getOutsideWallPaper();
            int outsideWallThick = structureDetail.getOutsideWallThick();
            String standard = _pStandard(outsideWallType, outsideWallPaper) + String.valueOf(outsideWallThick);
            Price price = estimatePriceService.showPrice("P", "O", outsideWallType, outsideWallPaper);
            int startPrice = 0, gapPrice = 0, maxPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                gapPrice = price.getGapPrice();
                total = (startPrice + gapPrice * (outsideWallThick - 50) / 25) * _OA;
                ePrice = price.getEPrice();
                uPrice = startPrice + gapPrice * (outsideWallThick - 50) / 25;
            }
            calArray.add("외벽판넬|"+ standard + "|M²|"+ String.valueOf(_OA) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println(standard + "|M²|"+ _OA + "|" + ":외벽판넬 SAVE");
        }
        _NA = (long)Math.ceil(_NA / 1000000.0D);
        if (_NA != 0L) {
            String insideWallType = structureDetail.getInsideWallType();
            String insideWallPaper = structureDetail.getInsideWallPaper();
            int insideWallThick = structureDetail.getInsideWallThick();
            String standard = _pStandard(insideWallType, insideWallPaper) + String.valueOf(insideWallThick);
            Price price = estimatePriceService.showPrice("P", "I", insideWallType, insideWallPaper);
            int startPrice = 0, gapPrice = 0, maxPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                gapPrice = price.getGapPrice();
                total = (startPrice + gapPrice * (insideWallThick - 50) / 25) * _NA;
                ePrice = price.getEPrice();
                uPrice = startPrice + gapPrice * (insideWallThick - 50) / 25;
            }
            calArray.add("내벽판넬|"+ standard + "|M²|"+ String.valueOf(_NA) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println(standard + "|M²|"+ _NA + "|" + ":내벽판넬 SAVE");
        }
        _CA = (long)Math.ceil(_CA / 1000000.0D);
        if (_CA != 0L) {
            String ceilingType = structureDetail.getCeilingType();
            String ceilingPaper = structureDetail.getCeilingPaper();
            int ceilingThick = structureDetail.getCeilingThick();
            String standard = _pStandard(ceilingType, ceilingPaper) + String.valueOf(ceilingThick);
            Price price = estimatePriceService.showPrice("P", "C", ceilingType, ceilingPaper);
            int startPrice = 0, gapPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                gapPrice = price.getGapPrice();
                total = (startPrice + gapPrice * (ceilingThick - 50) / 25) * _CA;
                ePrice = price.getEPrice();
                uPrice = startPrice + gapPrice * (ceilingThick - 50) / 25;
            }

            calArray.add("천장판넬|"+ standard + "|M²|"+ String.valueOf(_CA) + "|" + total + "|" + ePrice + "|" + uPrice);
            calArray.add("천장부자재| |M²|"+ String.valueOf(_CA) + "|" + (_CA * 10000L) + "|" + ePrice + "|" + uPrice);
            System.out.println(standard + "|M²|"+ _CA + "|" + ":천장판넬 SAVE");
        }
        _PA = (long)Math.ceil(_PA / 1000000.0D);
        if (_PA != 0L) {
            Price price = estimatePriceService.showPrice("PR", "", "", "");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                total = startPrice * _PA;
                ePrice = price.getEPrice();
                uPrice = startPrice;
            }
            calArray.add("파라펫판넬|EPS,소골,비난연,50|M²|"+ String.valueOf(_PA) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("파라펫판넬|EPS,소골,비난연,50|M²|"+ _PA + "|" + ":파라펫판넬 SAVE");
        }
        _KA = (long)Math.ceil(_KA / 1000000.0D);
        if (_KA != 0L) {
            Price price = estimatePriceService.showPrice("B", "", "", "");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                total = startPrice * _KA;
                ePrice = price.getEPrice();
                uPrice = startPrice;
            }
            calArray.add("보온판넬|EPS,소골,비난연,50|M²|"+ String.valueOf(_KA) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("보온판넬|EPS,소골,비난연,50|M²|"+ _KA + "|" + ":보온판넬 SAVE");
        }
        _CNA = (long)Math.ceil(_CNA / 1000.0D);
        if (_CNA != 0L) {
            Price price = estimatePriceService.showPrice("C", "", "", "");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                total = startPrice * _CNA;
                uPrice = startPrice;
            }
            calArray.add("캐노피판넬|EPS,소골,비난연,50|M²|"+ String.valueOf(_CNA) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("|M²|"+ _CNA + "|" + ":캐노피 SAVE");
        }
        if (_RAS_RT != 0L) {
            Price price = estimatePriceService.showPrice("S01", "R", "용마루", "");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                total = (startPrice * standardPrice) * _RAS_RT;
                uPrice = startPrice * standardPrice;
            }
            calArray.add("용마루상부|W=640|EA|"+ String.valueOf(_RAS_RT) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("|EA²|"+ String.valueOf(_RAS_RT) + "|" + ":용마루상부 SAVE");
        }
        if (_RAS_RB != 0L) {
            Price price = estimatePriceService.showPrice("S01", "R", "용마루하부", "무");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                total = (startPrice * standardPrice) * _RAS_RB;
                uPrice = startPrice * standardPrice;
            }
            calArray.add("용마루하부|W=420|EA|"+ String.valueOf(_RAS_RB) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("|M²|"+ String.valueOf(_RAS_RB) + "|" + ":용마루하부 SAVE");
        }
        if (_RAS_RH != 0L) {
            Price price = estimatePriceService.showPrice("S01", "R", "반트러스용마루", "무");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                total = (startPrice * standardPrice) * _RAS_RH;
                uPrice = startPrice * standardPrice;
            }
            calArray.add("반트러스용마루|W=500|EA|"+ String.valueOf(_RAS_RH) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("|M²|"+ String.valueOf(_RAS_RH) + "|" + ":반트러스용마루 SAVE");
        }
        if (_RAS_C != 0L) {
            Price price = estimatePriceService.showPrice("S02", "", "크로샤", "무");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                total = startPrice * _RAS_C;
                uPrice = startPrice;
            }
            calArray.add("크로샤| |EA|" + String.valueOf(_RAS_C) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("|M²|"+ String.valueOf(_RAS_C) + "|" + ":크로샤 SAVE");
        }
        if (_OAS_DH != 0L) {
            Price price = estimatePriceService.showPrice("S01", "O", "두겹후레싱", "유");
            int _oas_dh_w = 0;
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                _oas_dh_w = standardPrice + oomt;
                total = (startPrice * _oas_dh_w) * _OAS_DH;
                uPrice = startPrice * _oas_dh_w;
            }
            calArray.add("두겹후레싱|w=" + String.valueOf(_oas_dh_w) + "|EA|" + String.valueOf(_OAS_DH) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println(String.valueOf(_oas_dh_w) +"::" + String.valueOf(_OAS_DH) + ":두겹후레싱 SAVE");
        }
        if (_RAS_B != 0L) {
            Price price = estimatePriceService.showPrice("S01", "R", "미돌출박공", "유");
            int _ras_b_w = 0;
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                _ras_b_w = standardPrice + roomt;
                total = (startPrice * _ras_b_w) * _RAS_B;
                uPrice = startPrice * _ras_b_w;
            }
            calArray.add("미돌출박공|w=" + String.valueOf(_ras_b_w) + "|EA|" + String.valueOf(_RAS_B) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println(String.valueOf(_ras_b_w) +"::" + String.valueOf(_RAS_B) + ":미돌출박공 SAVE");
        }
        if (_RAS_B2 != 0L) {
            Price price = estimatePriceService.showPrice("S01", "R", "미돌출박공", "유");
            int _ras_b2_w = 0;
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                _ras_b2_w = standardPrice + oomt;
                total = (startPrice * _ras_b2_w) * _RAS_B2;
                uPrice = startPrice * _ras_b2_w;
            }
            calArray.add("돌출박공|w=" + String.valueOf(_ras_b2_w) + "|EA|" + String.valueOf(_RAS_B2) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println(String.valueOf(_ras_b2_w) +"::" + String.valueOf(_RAS_B2) + ":돌출박공 SAVE");
        }
        if (_RAS_SW != 0L) {
            Price price = estimatePriceService.showPrice("S01", "R", "물끊기", "무");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                total = (startPrice * standardPrice) * _RAS_SW;
                uPrice = startPrice * standardPrice;
            }
            calArray.add("물끊기|w=350|EA|" + String.valueOf(_RAS_SW) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println(String.valueOf(_RAS_SW) + ":물끊기 SAVE");
        }
        if (_RAS_W != 0L) {
            Price price = estimatePriceService.showPrice("S01", "R", "물받이", "유");
            int _ras_w_w = 0;
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                _ras_w_w = standardPrice + oomt;
                total = (startPrice * _ras_w_w) * _RAS_W;
                uPrice = _ras_w_w * startPrice;
            }
            calArray.add("물받이|w="+String.valueOf(_ras_w_w)+"|EA|" + String.valueOf(_RAS_W) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println(String.valueOf(_ras_w_w) +"::"+String.valueOf(_RAS_W)+ ":물받이 SAVE");
        }
        if (_RAS_W2 != 0L) {
            Price price = estimatePriceService.showPrice("S01", "R", "물받이", "유");
            int _ras_w_w2 = 0;
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                _ras_w_w2 = standardPrice + oomt;
                total = (startPrice * _ras_w_w2) * _RAS_W2;
                uPrice = _ras_w_w2 * startPrice;
            }
            calArray.add("SUB물받이|w="+String.valueOf(_ras_w_w2)+"|EA|" + String.valueOf(_RAS_W2) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println(String.valueOf(_ras_w_w2) +"::"+String.valueOf(_RAS_W2)+ ":SUB물받이 SAVE");
        }
        if (_RAS_WV != 0L) {
            System.out.println("::" + String.valueOf(_RAS_WV) + ":_RAS_WV SAVE");
            calArray.add("물받이커버| |EA|" + String.valueOf(_RAS_WV) + "|" + String.valueOf(_RAS_WV) + "|0|0");
        }
        if (_RAS_WB != 0L) {
            int roofThick = structureDetail.getRoofThick();
            Price price = estimatePriceService.showPrice("S03", "PVC물받이받침대", String.valueOf(roofThick) + "티판넬용", "");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                total = startPrice * _RAS_WB;
                uPrice = startPrice;
            }
            calArray.add("물받이받침대| |EA|" + String.valueOf(_RAS_WB) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("::"+String.valueOf(_RAS_WB)+ ":물받이받침대 SAVE");
        }
        if (_RAS_WC != 0L) {
            calArray.add("물받이받침쇠| |EA|" + String.valueOf(_RAS_WC) + "|" + String.valueOf(_RAS_WC) + "|0|1");
            System.out.println("::" + String.valueOf(_RAS_WC) + ":물받이받침쇠 SAVE");
        }
        if (_RAS_EC != 0L) {
            calArray.add("앤드캡| |EA|" + String.valueOf(_RAS_EC) + "|" + (_RAS_EC * 5000L) + "|0|1");
            System.out.println("::" + String.valueOf(_RAS_EC) + ":앤드캡 SAVE");
        }
        _EC = (long)Math.ceil(_EC / 1000000.0D);
        if (_EC != 0L) {
            calArray.add("외벽삭감| |EA|" + String.valueOf(_EC) + "|0|0|1");
            System.out.println("::" + String.valueOf(_EC) + ":외벽삭감 SAVE");
        }
        if (_OAS_CB != 0L) {
            Price price = estimatePriceService.showPrice("S01", "O", "의자베이스", "유");
            int _oas_cb_w = 0;
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                _oas_cb_w = standardPrice + oomt;
                total = (startPrice * _oas_cb_w) * _OAS_CB;
                ePrice = price.getEPrice();
                uPrice = startPrice * _oas_cb_w;
            }
            calArray.add("의자베이스|w="+String.valueOf(_oas_cb_w)+"|EA|" + String.valueOf(_OAS_CB) + "|" + String.valueOf(_RAS_WC) + "|0|1");
            System.out.println(String.valueOf(_oas_cb_w)+"::" + String.valueOf(_OAS_CB) + ":의자베이스 SAVE");
        }
        if (_OAS_OC != 0L) {
            Price price = estimatePriceService.showPrice("S01", "O", "외부코너", "유");
            int _oas_cc_w = 0;
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                _oas_cc_w = standardPrice + oomt;
                total = (startPrice * _oas_cc_w) * _OAS_OC;
                uPrice = startPrice * _oas_cc_w;
            }
            calArray.add("외부코너|w="+ String.valueOf(_oas_cc_w) + "|EA|" + String.valueOf(_OAS_OC) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println(String.valueOf(_oas_cc_w) + "::" + String.valueOf(_OAS_OC) + ":외부코너 SAVE");
        }
        if (_NAS_UB != 0L) {
            Price price = estimatePriceService.showPrice("S01", "I", "유바", "유");
            int _oas_oc_w = 0;
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                _oas_oc_w = standardPrice + oomt;
                total = (startPrice * _oas_oc_w) * _NAS_UB;
                uPrice = startPrice * _oas_oc_w;
            }
            calArray.add("유바|w="+ String.valueOf(_oas_oc_w) + "|EA|" + String.valueOf(_NAS_UB) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println(String.valueOf(_oas_oc_w) + "::" + String.valueOf(_NAS_UB) + ":유바 SAVE");
        }
        if (_CNAS_S1 != 0L) {
            Price price = estimatePriceService.showPrice("S02", "", "캐노피삼각대", "무");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                total = startPrice * _CNAS_S1;
                uPrice = startPrice;
            }
            calArray.add("캐노피삼각대|아이보리,은회색|EA|"+ String.valueOf(_CNAS_S1) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("::" + String.valueOf(_CNAS_S1) + ":캐노피삼각대 SAVE");
        }
        if (_CNAS_SW != 0L) {
            Price price = estimatePriceService.showPrice("S02", "", "캐노피 상부마감(물끊기)", "무");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                total = startPrice * _CNAS_SW;
                uPrice = startPrice;
            }
            calArray.add("캐노피상부마감| |EA|" + String.valueOf(_CNAS_SW) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("::" + String.valueOf(_CNAS_SW) + ":캐노피상부마감 SAVE");
        }
        if (_CNAS_S2 != 0L) {
            Price price = estimatePriceService.showPrice("S02", "", "캐노피정면마감", "무");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                total = price.getStartPrice() * _CNAS_S2;
                uPrice = price.getStartPrice();
            }
            calArray.add("캐노피정면마감| |EA|" + String.valueOf(_CNAS_S2) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("::" + String.valueOf(_CNAS_S2) + ":캐노피정면마감 SAVE");
        }
        if (_CNAS_S3 != 0L) {
            Price price = estimatePriceService.showPrice("S02", "", "캐노피 측면마감셋", "무");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                total = startPrice * _CNAS_S3;
                uPrice = startPrice;
            }
            calArray.add("캐노피측면마감| |set|" + String.valueOf(_CNAS_S3) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("::" + String.valueOf(_CNAS_S3) + ":캐노피측면마감 SAVE");
        }
        if (gd != 0) {
            Price price = estimatePriceService.showPrice("S03", "구찌", String.valueOf(gd) + "DIA", "");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                total = (startPrice * ge);
                uPrice = startPrice;
            }
            calArray.add("구찌|"+ String.valueOf(gd) + "DIA|EA|" + String.valueOf(ge) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println(String.valueOf(gd) + "::" + String.valueOf(ge) + ":구찌 SAVE");
        }
        if (gdi != 0) {
            Price price = estimatePriceService.showPrice("S03", "구찌", String.valueOf(gd) + "DIA", "");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            String name = "구찌안쪽";
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                total = (startPrice * gei);
                uPrice = startPrice;
            }
            if (type.equals("SL"))
                name = "옥탑구찌";
            calArray.add(name + "|" + String.valueOf(gdi) + "DIA|EA|" + String.valueOf(gei) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println(String.valueOf(gdi) + "::" + String.valueOf(gei) + ":구찌안쪽드레인 SAVE");
        }
        if (_RAS_RL2 != 0L) {
            Price price = estimatePriceService.showPrice("S03", "상자홈통", "", "");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                total = startPrice * _RAS_RL2;
                uPrice = startPrice;
            }
            calArray.add("상자홈통| |M|" + String.valueOf(_RAS_RL2) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("::" + String.valueOf(_RAS_RL2) + ":상자홈통 SAVE");
        }
        _RAS_RL /= 1000L;
        if (_RAS_RL != 0L) {
            Price price = estimatePriceService.showPrice("S03", "선홈통", String.valueOf(gd) + "파이", "");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                total = startPrice * _RAS_RL;
                ePrice = price.getEPrice();
                uPrice = startPrice;
            }
            calArray.add("선홈통| |M|" + String.valueOf(_RAS_RL) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("::" + String.valueOf(_RAS_RL) + ":선홈통 SAVE");
        }
        if (_RAS_RLS != 0L) {
            Price price = estimatePriceService.showPrice("S03", "선홈통반도", String.valueOf(gd) + "파이", "");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                total = startPrice * _RAS_RLS;
                uPrice = startPrice;
            }
            calArray.add("반도| |EA|" + String.valueOf(_RAS_RLS) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("::" + String.valueOf(_RAS_RLS) + ":반도 SAVE");
        }
        if (_RAS_S != 0L) {
            Price price = estimatePriceService.showPrice("S03", "연결소켓", String.valueOf(gd) + "파이", "");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                total = startPrice * _RAS_S;
                uPrice = startPrice;
            }
            calArray.add("소켓| |EA|" + String.valueOf(_RAS_S) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("::" + String.valueOf(_RAS_S) + ":소켓 SAVE");
        }
        if (_RAS_RE != 0L) {
            Price price = estimatePriceService.showPrice("S03", "엘보", String.valueOf(gd) + "파이", "");
            int startPrice = 0, gapPrice = 0, maxPrice = 0, standardPrice = 0, ePrice = 0, uPrice = 0;
            long total = 0L;
            if (price != null) {
                startPrice = price.getStartPrice();
                standardPrice = price.getStandardPrice();
                total = startPrice * _RAS_RE;
                uPrice = startPrice;
            }
            calArray.add("엘보| |EA|" + String.valueOf(_RAS_RE) + "|" + total + "|" + ePrice + "|" + uPrice);
            System.out.println("::" + String.valueOf(_RAS_RE) + ":엘보 SAVE");
        }
        if (_EAS_S1 != 0L) {
            calArray.add("기타부재료|볼트,피스,실리콘 등|M²|"+ String.valueOf(_AA) + "|" + String.valueOf(_EAS_S1) + "|0|" + String.valueOf(_EAS_S1_UC));
            System.out.println("::" + String.valueOf(_EAS_S1) + ":기타부재료 SAVE");
        }
        if (_EAS_S2 != 0L) {
            calArray.add("장비대|지게차,스카이,렌탈|M²|"+ String.valueOf(_AA) + "|" + String.valueOf(_EAS_S2) + "|0|" + String.valueOf(_EAS_S2_UC));
            System.out.println("::" + String.valueOf(_EAS_S2) + ":장비대 SAVE" + String.valueOf(_AA));
            calArray.add("운반비|판넬,부자재 등|M²|" + String.valueOf(_AA) + "|" + String.valueOf(_EAS_S2) + "|0|" + String.valueOf(_EAS_S2_UC));
            System.out.println("::" + String.valueOf(_EAS_S2) + ":운반비 SAVE");
        }
        if (_EAS_S3 != 0L) {
            calArray.add("폐기물| |M²|"+ String.valueOf(_AA) + "|" + String.valueOf(_EAS_S3) + "|0|" + String.valueOf(_EAS_S3_UC));
            System.out.println("::" + String.valueOf(_EAS_S3) + ":폐기물 SAVE");
        }
        return calArray;
    }

    public static void cal(String type) {
        long v;
        long l;
        long a;
        long m;
        long r;
        int i;
        long sl_r;
        switch (type) {
            case "AT":
                _oatCal();
                _a = sw * (long)Math.ceil(sh / 1000.0D) * 1000L * 2L;
                _c = si * (long)Math.ceil(sh / 1000.0D) * 1000L * 2L;
                _OA = _a + _b + _c - _DA - _WA;
                _OAS_OC = (long)Math.ceil(sh / 2950.0D) * 4L;
                _oasCalCb();
                _RA = (long)Math.ceil(si / 1000.0D) * 1000L * (long)(Math.sqrt(Math.pow(st, 2.0D) + Math.pow((sw / 2), 2.0D)) + oomt + 150.0D) * 2L;
                if ((((0 < si % 1000) ? 1 : 0) & ((si % 1000 <= 300) ? 1 : 0)) != 0)
                    _RA = (long)(_RA - (Math.sqrt(Math.pow(st, 2.0D) + Math.pow((sw / 2), 2.0D)) + oomt + 150.0D) * 1000.0D);
                _RAS_RT = (long)Math.ceil(si / 2950.0D);
                _RAS_RB = (long)Math.ceil(si / 2950.0D);
                _RAS_RH = 0L;
                _RAS_C = (long)Math.ceil(si / 1000.0D) * 2L;
                _RAS_B = (long)Math.ceil((Math.sqrt(Math.pow(st, 2.0D) + Math.pow((sw / 2), 2.0D)) + oomt + 150.0D) * 2.0D / 2950.0D) * 2L;
                _RAS_B2 = 0L;
                _RAS_W = (long)Math.ceil(si / 2950.0D) * 2L;
                _RAS_W2 = 0L;
                _RAS_WB = (long)(Math.ceil(si / 900.0D) + 1.0D) * 2L;
                _RAS_RLS = (long)(Math.floor((sh / 1500)) + 1.0D) * ge;
                _RAS_RE = ge;
                _RAS_SW = 0L;
                if (sh >= 6000) {
                    _RAS_S = (long)Math.floor((sh / 4000)) * ge;
                    break;
                }
                _RAS_S = 0L;
                break;
            case "BT":
                _oatCal();
                _a = sw * (long)Math.ceil(sh / 1000.0D) * 1000L * 2L;
                _c = (long)(si * Math.ceil(sh / 1000.0D) * 1000.0D) + (long)(si * Math.ceil((sh + st) / 1000.0D) * 1000.0D);
                _OA = _a + _b + _c - _DA - _WA;
                _OAS_OC = (long)Math.ceil(sh / 2950.0D) * 2L + (long)Math.ceil((sh + st) / 2950.0D) * 2L;
                _oasCalCb();
                _RA = (long)Math.ceil(si / 1000.0D) * 1000L * (long)(Math.sqrt(Math.pow(st, 2.0D) + Math.pow(sw, 2.0D)) + oomt + 150.0D);
                _RAS_RT = 0L;
                _RAS_RB = 0L;
                _RAS_RH = (long)Math.ceil(si / 2950.0D);
                _RAS_C = (long)Math.ceil(si / 1000.0D);
                _RAS_B = (long)Math.ceil((Math.sqrt(Math.pow(st, 2.0D) + Math.pow(sw, 2.0D)) + oomt + 150.0D) * 2.0D / 2950.0D);
                _RAS_B2 = 0L;
                _RAS_W = (long)Math.ceil(si / 2950.0D);
                _RAS_W2 = 0L;
                _RAS_WB = (long)Math.ceil(si / 900.0D) + 1L;
                _RAS_RLS = (long)(Math.floor((sh / 1500)) + 1.0D) * ge;
                _RAS_RE = ge;
                _RAS_SW = 0L;
                if (sh >= 6000) {
                    _RAS_S = (long)Math.floor((sh / 4000)) * ge;
                    break;
                }
                _RAS_S = 0L;
                break;
            case "BE":
                r = 0L;
                v = (sb + sh % 1000);
                l = (long)Math.ceil(v / 1000.0D);
                a = st - l * 1000L + (sh % 1000);
                m = (long)Math.ceil(a / 1000.0D);
                for (i = 0; i < m; i++)
                    r = (long)(r + Math.ceil(((sw * (a - (i * 1000)) + sc * (a - (i * 1000))) / (st - sb))));
                _b = r * 2L * 1000L + ((sc + sw) * 1000) * l * 2L;
                _a = sw * (long)Math.floor((sh / 1000)) * 1000L * 2L;
                _c = si * (long)Math.ceil((sh + st) / 1000.0D) * 1000L + si * (long)Math.ceil((sh + sb) / 1000.0D) * 1000L;
                _c += si * (long)Math.ceil(sc / 1000.0D) * 1000L;
                _OA = _a + _b + _c - _DA - _WA;
                _OAS_OC = (long)Math.ceil((sh + sb) / 2950.0D) * 2L + (long)Math.ceil((sh + st) / 2950.0D) * 2L + (long)Math.ceil((si + sc) / 2950.0D) * 2L;
                _oasCalCb();
                _RA = (long)Math.ceil(si / 1000.0D) * 1000L * (long)(Math.sqrt(Math.pow((st - sb), 2.0D) + Math.pow((sw + sc), 2.0D)) + oomt + 150.0D);
                _RAS_RT = 0L;
                _RAS_RB = 0L;
                _RAS_RH = (long)Math.ceil(si / 2950.0D);
                _RAS_C = (long)Math.ceil(si / 1000.0D);
                _RAS_B = (long)Math.ceil((Math.sqrt(Math.pow(st, 2.0D) + Math.pow((sw + sc), 2.0D)) + oomt + 150.0D) / 2950.0D) * 2L;
                _RAS_B2 = 0L;
                _RAS_W = (long)Math.ceil(si / 2950.0D);
                _RAS_W2 = 0L;
                _RAS_WB = (long)Math.ceil(si / 900.0D) + 1L;
                _RAS_RLS = (long)(Math.floor((sh / 1500)) + 1.0D) * ge;
                _RAS_RE = ge;
                _RAS_SW = 0L;
                if (sh >= 6000) {
                    _RAS_S = (long)Math.floor((sh / 4000)) * ge;
                    break;
                }
                _RAS_S = 0L;
                break;
            case "AB":
                _b = 0L;
                _a = sw * (long)Math.ceil((sh + st) / 1000.0D) * 1000L * 2L;
                _c = si * (long)Math.ceil((sh + st) / 1000.0D) * 1000L * 2L;
                _OA = _a + _b + _c - _DA - _WA;
                _OAS_OC = (long)Math.ceil(((sh + st) * 2) / 2950.0D) * 2L;
                _OAS_DH = (long)Math.ceil(((si + sw) * 2) / 2950.0D);
                _oasCalCb();
                _RA = (long)Math.ceil(si / 1000.0D) * 1000L * (long)Math.sqrt(Math.pow(st, 2.0D) + Math.pow((sw / 2), 2.0D)) * 2L;
                if ((((0 < si % 1000) ? 1 : 0) & ((si % 1000 <= 300) ? 1 : 0)) != 0)
                    _RA = (long)(_RA - Math.sqrt(Math.pow(st, 2.0D) + Math.pow((sw / 2), 2.0D)) * 1000.0D);
                _RAS_RT = (long)Math.ceil(si / 2950.0D);
                _RAS_RB = (long)Math.ceil(si / 2950.0D);
                _RAS_RH = 0L;
                _RAS_C = (long)Math.ceil(si / 1000.0D) * 2L;
                _RAS_B = 0L;
                _RAS_B2 = 0L;
                _RAS_W = 0L;
                _RAS_W2 = (long)Math.ceil(si / 2950.0D) * 2L;
                _RAS_WB = 0L;
                _RAS_RLS = (long)(Math.floor((sh / 1500)) + 1.0D) * ge;
                _RAS_RE = ge;
                _RAS_SW = (long)Math.ceil(Math.sqrt(Math.pow(st, 2.0D) + Math.pow((sw / 2), 2.0D)) * 2.0D / 2960.0D) * 2L;
                if (sh >= 6000) {
                    _RAS_S = (long)Math.floor((sh / 4000)) * ge;
                } else {
                    _RAS_S = 0L;
                }
                _KA = (si * 2 * 1000);
                _paCal(type);
                break;
            case "BB":
                _b = 0L;
                _a = sw * (long)Math.ceil((sh + st) / 1000.0D) * 1000L * 2L;
                _c = si * (long)Math.ceil((sh + st) / 1000.0D) * 1000L;
                _c += si * (long)Math.ceil(sh / 1000.0D) * 1000L;
                _OA = _a + _b + _c - _DA - _WA;
                _OAS_OC = (long)Math.ceil(sh / 2950.0D) * 2L + (long)Math.ceil((sh + st) / 2950.0D) * 2L;
                _OAS_DH = (long)Math.ceil((si + sw * 2) / 2950.0D) + (long)Math.ceil(st / 2950.0D) * 2L;
                _oasCalCb();
                _RA = (long)Math.ceil(si / 1000.0D) * 1000L * (long)(Math.sqrt(Math.pow(st, 2.0D) + Math.pow(sw, 2.0D)) + oomt + 150.0D);
                _RAS_RT = 0L;
                _RAS_RB = 0L;
                _RAS_RH = 0L;
                _RAS_C = (long)Math.ceil(si / 1000.0D);
                _RAS_B = 0L;
                _RAS_B2 = 0L;
                _RAS_W = (long)Math.ceil(si / 2950.0D);
                _RAS_W2 = 0L;
                _RAS_WB = (long)Math.ceil(si / 900.0D) + 1L;
                _RAS_RLS = (long)(Math.floor((sh / 1500)) + 1.0D) * ge;
                _RAS_RE = ge;
                _RAS_SW = (long)Math.ceil((Math.sqrt(Math.pow(st, 2.0D) + Math.pow(sw, 2.0D)) + oomt + 150.0D) / 2960.0D) * 2L + (long)Math.ceil(si / 2960.0D);
                if (sh >= 6000) {
                    _RAS_S = (long)Math.floor((sh / 6000)) * ge;
                } else {
                    _RAS_S = 0L;
                }
                _paCal(type);
                break;
            case "AE":
                _oatCal();
                _a = sw * (long)Math.ceil(sh / 1000.0D) * 1000L * 2L;
                _c = si * (long)Math.ceil(sh / 1000.0D) * 1000L * 2L;
                _OA = _a + _b + _c - _DA - _WA;
                _OAS_OC = (long)Math.ceil(sh / 2950.0D) * 4L;
                _oasCalCb();
                _RA = (long)Math.ceil((si + sc * 2) / 1000.0D) * 1000L * (long)Math.sqrt(Math.pow((st + st * sc / sw / 2), 2.0D) + Math.pow((sw / 2 + sc), 2.0D)) * 2L;
                if ((((0 < (si + sc) % 1000) ? 1 : 0) & (((si + sc) % 1000 <= 300) ? 1 : 0)) != 0)
                    _RA = (long)(_RA - (Math.sqrt(Math.pow(st, 2.0D) + Math.pow((sw / 2), 2.0D) + sc) + oomt + 150.0D) * 1000.0D);
                _RAS_RT = (long)Math.ceil((si + sc) / 2950.0D);
                _RAS_RB = (long)Math.ceil((si + sc) / 2950.0D);
                _RAS_RH = 0L;
                _RAS_C = (long)Math.ceil((si + sc * 2) / 1000.0D) * 2L;
                _RAS_B = 0L;
                _RAS_B2 = (long)Math.ceil(Math.sqrt(Math.pow((st + st * sc * 2 / sw), 2.0D) + Math.pow((sw / 2), 2.0D) + sc) / 2950.0D) * 4L;
                _RAS_W = 0L;
                _RAS_W2 = 0L;
                _RAS_WB = 0L;
                _RAS_EC = (long)Math.ceil((si + sc * 2) / 1000.0D) * 2L;
                _RAS_RLS = 0L;
                _RAS_RE = 0L;
                _RAS_SW = 0L;
                _RAS_S = 0L;
                break;
            case "AG":
                swt = (long)Math.ceil(((sw - swi) / 2));
                sit = (long)Math.ceil(((si - sii) / 2));
                sir = si - sit;
                swr = sw - swt;
                swrh = (long)Math.ceil(Math.sqrt(Math.pow(st, 2.0D) + Math.pow(swt, 2.0D)));
                swrhi = swrh;
                sirh = (long)Math.ceil(Math.sqrt(Math.pow(st, 2.0D) + Math.pow(sit, 2.0D)));
                sirhi = sirh;
                _a = ((sw + si) * 2 - swi - sii) * (long)Math.ceil((sh + st) / 1000.0D) * 1000L;
                _c = (sii + swi) * (long)Math.ceil(sh / 1000.0D) * 1000L;
                _OA = _a + _b + _c - _DA - _WA;
                _OAS_OC = (long)Math.ceil((sh + st) / _comp("oc")) * 3L + (long)Math.ceil(sh / _comp("oc")) * 3L + (long)Math.ceil(st / _comp("oc")) * 3L;
                _OAS_DH = (long)Math.ceil(si / _comp("dh")) + (long)Math.ceil(sw / _comp("dh")) + (long)Math.ceil((swt * 2L) / _comp("dh")) + (long)Math.ceil((sit * 2L) / _comp("dh")) + (long)Math.ceil(st / _comp("dh")) * 2L;
                _OAS_CB = (long)Math.ceil(((si + sw) * 2) / _comp("cb"));
                _RA = (long)(_RA + Math.ceil(sir / 1000.0D) * 1000.0D * swrh);
                _RA = (long)(_RA + Math.ceil(sii / 1000.0D) * 1000.0D * ((swrhi + oomt) + _comp("rc")));
                _RA = (long)(_RA + Math.ceil(swr / 1000.0D) * 1000.0D * sirh);
                _RA = (long)(_RA + Math.ceil(swi / 1000.0D) * 1000.0D * ((sirhi + oomt) + _comp("rc")));
                _RA = (long)(_RA + _rtCal(swrh, sir, sit, true) + _rtCal(swrhi, sii, sit, true) + _rtCal(sirh, swr, swt, true) + _rtCal(sirhi, swi, swt, true));
                _RAS_RT = (long)Math.ceil(sir / _comp("rt")) + (long)Math.ceil(swr / _comp("rt")) + (long)Math.ceil(Math.sqrt(Math.pow(swrh, 2.0D) + Math.pow(sit, 2.0D)) / _comp("rt"));
                _RAS_RB = (long)Math.ceil(sir / _comp("rt")) + (long)Math.ceil(swr / _comp("rt")) + (long)Math.ceil(Math.sqrt(Math.pow(swrh, 2.0D) + Math.pow(sit, 2.0D)) / _comp("rt"));
                _RAS_RH = 0L;
                _RAS_SW = (long)Math.ceil((swrh + swrhi) / _comp("sw")) + (long)Math.ceil((sirh + sirhi) / _comp("sw"));
                _RAS_C = (long)Math.ceil(sir / 1000.0D) + (long)Math.ceil(swr / 1000.0D) + (long)Math.ceil(sw / 1000.0D) + (long)Math.ceil(si / 1000.0D);
                _RAS_B = 0L;
                _RAS_B2 = 0L;
                _RAS_W = (long)Math.ceil(sii / _comp("w")) + (long)Math.ceil(swi / _comp("w"));
                _RAS_W2 = (long)Math.ceil(si / _comp("w")) + (long)Math.ceil(sw / _comp("w")) + (long)Math.ceil(Math.sqrt(Math.pow(sirhi, 2.0D) + Math.pow(swt, 2.0D)) / _comp("w"));
                _RAS_WV = (long)Math.ceil(Math.sqrt(Math.pow(sirhi, 2.0D) + Math.pow(swt, 2.0D)) / _comp("w"));
                _RAS_WB = (long)Math.ceil(sii / _comp("wb")) + (long)Math.ceil(swi / _comp("wb")) + 2L;
                _RAS_WC = (long)Math.ceil(si / _comp("wc")) + (long)Math.ceil(sw / _comp("wc")) + (long)Math.ceil(Math.sqrt(Math.pow(sirhi, 2.0D) + Math.pow(swt, 2.0D)) / _comp("wc")) + 3L;
                _RAS_RL = (long)Math.ceil((sh * (gei + ge)));
                _RAS_RL2 = ge;
                _RAS_RLS = ((long)Math.floor(sh / _comp("rls")) + 1L) * (gei + ge);
                _RAS_RE = (gei + ge);
                skcal("s");
                _KA = (long)(_KA + Math.ceil(Math.sqrt(Math.pow(sit, 2.0D) + Math.pow(swrhi, 2.0D))) * 1000.0D);
                _PA = (long)_rtCal(swt, 0.0D, st, false) * 2L + (long)_rtCal(sit, 0.0D, st, false) * 2L + (si + sw) * (long)Math.ceil(st / 1000.0D) * 1000L;
                break;
            case "BG":
                swt = (long)Math.ceil((sw - swi));
                sit = (long)Math.ceil((si - sii));
                swrh = (long)Math.ceil(Math.sqrt(Math.pow(st, 2.0D) + Math.pow(swt, 2.0D)));
                sirh = (long)Math.ceil(Math.sqrt(Math.pow(st, 2.0D) + Math.pow(sit, 2.0D)));
                _a = ((sw + si) * 2 - swi - sii) * (long)Math.ceil((sh + st) / 1000.0D) * 1000L;
                _c = (sii + swi) * (long)Math.ceil(sh / 1000.0D) * 1000L;
                _OA = _a + _b + _c - _DA - _WA;
                _OAS_OC = (long)Math.ceil((sh + st) / _comp("oc")) * 3L + (long)Math.ceil(sh / _comp("oc")) * 3L;
                _OAS_DH = (long)Math.ceil(si / _comp("dh")) + (long)Math.ceil(sw / _comp("dh")) + (long)Math.ceil(swt / _comp("dh")) + (long)Math.ceil(sit / _comp("dh")) + (long)Math.ceil(st / _comp("dh")) * 2L;
                _OAS_CB = (long)Math.ceil(((si + sw) * 2) / _comp("cb"));
                _RA = (long)(_RA + ((long)Math.ceil(sii / 1000.0D) * 1000L) * ((swrh + oomt) + _comp("rc")));
                _RA = (long)(_RA + ((long)Math.ceil(swi / 1000.0D) * 1000L) * ((sirh + oomt) + _comp("rc")));
                _RA = (long)(_RA + _rtCal(swrh, sii, sit, true) + _rtCal(sirh, swi, swt, true));
                _RAS_RT = 0L;
                _RAS_RB = 0L;
                _RAS_RH = 0L;
                _RAS_SW = (long)Math.ceil(swrh / _comp("sw")) + (long)Math.ceil(sirh / _comp("sw")) + (long)Math.ceil(si / _comp("sw")) + (long)Math.ceil(sw / _comp("sw"));
                _RAS_C = (long)Math.ceil(si / 1000.0D) + (long)Math.ceil(sw / 1000.0D);
                _RAS_B = 0L;
                _RAS_B2 = 0L;
                _RAS_W = (long)Math.ceil(sii / _comp("w")) + (long)Math.ceil(swi / _comp("w"));
                _RAS_W2 = (long)Math.ceil((long)Math.sqrt(Math.pow(sirh, 2.0D) + Math.pow(swt, 2.0D)) / _comp("w"));
                _RAS_WV = _RAS_W2;
                _RAS_WB = (long)Math.ceil(sii / _comp("wb")) + (long)Math.ceil(swi / _comp("wb")) + 2L;
                _RAS_WC = (long)Math.ceil(Math.sqrt(Math.pow(sirh, 2.0D) + Math.pow(swt, 2.0D)) / _comp("wc")) + 1L;
                _RAS_RL = (long)Math.ceil((sh * ge));
                _RAS_RL2 = 0L;
                _RAS_RLS = ((long)Math.floor(sh / _comp("rls")) + 1L) * ge;
                _RAS_RE = ge;
                skcal("c");
                _KA = (long)(_KA + Math.ceil(Math.sqrt(Math.pow(sit, 2.0D) + Math.pow(swrh, 2.0D))) * 1000.0D);
                _PA = (long)_rtCal(swt, 0.0D, st, false) + (long)_rtCal(sit, 0.0D, st, false);
                break;
            case "SL":
                sl_r = 0L;
                _a = ((sw + si) * 2) * (long)Math.ceil((sh + sg) / 1000.0D) * 1000L;
                _a += (stw + sti) * (long)Math.ceil(sth / 1000.0D) * 1000L;
                if ((sh + sg) % 1000L != 0L)
                    sl_r = 1000L - (sh + sg) % 1000L;
                _a = (long)(_a + (stw + sti) * Math.ceil((sth - sg - sl_r) / 1000.0D) * 1000.0D);
                _OA = _a + _b + _c - _DA - _WA;
                _OAS_OC = (long)Math.ceil((sh + sg) / _comp("oc")) * 3L + (long)Math.ceil((sh + sth) / _comp("oc")) + (long)Math.ceil((sth - sg) / _comp("oc")) * 2L + (long)Math.ceil(sth / _comp("oc"));
                _OAS_DH = (long)Math.ceil((sw + si) / _comp("dh")) * 2L + (long)Math.ceil((stw + sti) / _comp("dh"));
                _OAS_CB = (long)Math.ceil(((si + sw) * 2) / _comp("cb"));
                _RAS_RLS = ((long)Math.floor(sh / 1500.0D) + 1L) * ge + ((long)Math.floor(sth / 1500.0D) + 1L) * gei;
                _RAS_RL += sth * gei;
                _RAS_RE = (ge + gei);
                skcal("c");
                _PA = (((sw + si) * 2) - stw - sti) * (long)Math.ceil(sg / 1000.0D) * 1000L;
                _OAS_OC += (long)Math.ceil((st * 3) / _comp("oc"));
                break;
            case "AX":
                _b = (long)_rtCal(sw, sh, st, true) * 4L;
                _a = sw * (long)Math.ceil(sh / 1000.0D) * 1000L;
                _c = si * (long)Math.ceil(sh / 1000.0D) * 1000L * 2L;
                _OA = _a + _b + _c - _DA - _WA;
                _OAS_OC = (long)Math.ceil(sh / 2950.0D) * 4L;
                _OAS_CB = (long)Math.ceil(((si * 2 + sw) - _DAS_W) / _comp("cb"));
                _RA = (long)Math.ceil(si / 1000.0D) * 1000L * (long)(Math.sqrt(Math.pow(st, 2.0D) + Math.pow((sw / 2), 2.0D)) + oomt + _comp("rc")) * 2L;
                if ((((0.0D < si % 1000.0D) ? 1 : 0) & ((si % 1000.0D <= 300.0D) ? 1 : 0)) != 0)
                    _RA = (long)(_RA - (Math.sqrt(Math.pow(st, 2.0D) + Math.pow((sw / 2), 2.0D)) + oomt + _comp("rc")) * 1000.0D);
                _RAS_RT = (long)Math.ceil(si / 2950.0D);
                _RAS_RB = (long)Math.ceil(si / 2950.0D);
                _RAS_RH = 0L;
                _RAS_C = (long)Math.ceil(si / 1000.0D) * 2L;
                _RAS_B = (long)Math.ceil((Math.sqrt(Math.pow(st, 2.0D) + Math.pow((sw / 2), 2.0D)) + oomt + _comp("rc")) / 2950.0D) * 4L;
                _RAS_B2 = 0L;
                _RAS_W = (long)Math.ceil(si / 2950.0D) * 2L;
                _RAS_W2 = 0L;
                _RAS_WB = (long)(Math.ceil(si / 900.0D) + 1.0D) * 2L;
                _RAS_RLS = (long)(Math.floor(sh / 1500.0D) + 1.0D) * ge;
                _RAS_RE = ge;
                _RAS_SW = 0L;
                skcal("c");
                break;
            case "BX":
                _b = (long)_rtCal(sw, sh, st, true) * 2L;
                _a = sw * (long)Math.ceil(sh / 1000.0D) * 1000L * 2L;
                _c = si * (long)Math.ceil(sh / 1000.0D) * 1000L;
                _EC = (long)(si * Math.floor((sh + st) / 1000.0D) * 1000.0D);
                _OA = _a + _b + _c - _DA - _WA;
                _OAS_OC = (long)Math.ceil(sh / _comp("oc")) * 2L + (long)Math.ceil((sh + st) / _comp("oc")) * 2L;
                _OAS_CB = (long)Math.ceil(((si + sw * 2) - _DAS_W) / _comp("cb"));
                _RA = (long)Math.ceil(si / 1000.0D) * 1000L * (long)(Math.sqrt(Math.pow(st, 2.0D) + Math.pow(sw, 2.0D)) + oomt + _comp("rc"));
                _RAS_RT = 0L;
                _RAS_RB = 0L;
                _RAS_RH = 0L;
                _RAS_C = (long)Math.ceil(si / 1000.0D);
                _RAS_B = (long)Math.ceil((Math.sqrt(Math.pow(st, 2.0D) + Math.pow(sw, 2.0D)) + oomt + _comp("rc")) / 2950.0D) * 2L;
                _RAS_B2 = 0L;
                _RAS_W = (long)Math.ceil(si / 2950.0D);
                _RAS_W2 = 0L;
                _RAS_WB = (long)Math.ceil(si / 900.0D) + 1L;
                _RAS_RLS = (long)(Math.floor(sh / 1500.0D) + 1.0D) * ge;
                _RAS_RE = ge;
                _RAS_SW = (long)Math.ceil(si / _comp("sw"));
                skcal("c");
                break;
            case "BBX":
                _b = 0L;
                _a = sw * (long)Math.ceil((sh + st) / 1000.0D) * 1000L * 2L;
                _c = si * (long)Math.ceil((sh + st) / 1000.0D) * 1000L;
                _EC = si * (long)Math.floor((sh + st) / 1000.0D) * 1000L;
                _OA = _a + _b + _c - _DA - _WA;
                _OAS_OC = (long)Math.ceil((sh + st) / _comp("oc")) * 4L + (long)Math.ceil(st / _comp("oc")) * 2L;
                _OAS_CB = (long)Math.ceil(((si + sw * 2) - _DAS_W) / _comp("cb"));
                _OAS_DH = (long)Math.ceil((si + sw * 2) / _comp("dh"));
                _RA = (long)Math.ceil(si / 1000.0D) * 1000L * (long)Math.sqrt(Math.pow(st, 2.0D) + Math.pow(sw, 2.0D));
                _RAS_RT = 0L;
                _RAS_RB = 0L;
                _RAS_RH = 0L;
                _RAS_C = (long)Math.ceil(si / 1000.0D);
                _RAS_B = 0L;
                _RAS_B2 = 0L;
                _RAS_W = 0L;
                _RAS_W2 = (long)Math.ceil(si / _comp("w"));
                _RAS_WB = 0L;
                _RAS_WC = (long)Math.ceil(si / _comp("wb")) + 1L;
                _RAS_RL = (sh * ge);
                _RAS_RL2 = ge;
                _RAS_RLS = ((long)Math.floor(sh / _comp("rls")) + 1L) * ge;
                _RAS_RE = ge;
                _RAS_SW = (long)Math.ceil(si / _comp("sw")) + (long)Math.ceil(Math.sqrt(Math.pow(st, 2.0D) + Math.pow(sw, 2.0D)) / _comp("sw")) * 2L;
                skcal("c");
                _PA = (long)_rtCal(sw, 0.0D, st, false) * 2L;
                _PA += si * (long)Math.ceil(st / 1000.0D) * 1000L;
                break;
        }
    }

    public static String _pStandard(String type, String subType) {
        String typeKr = "";
        String subTypeKr = "";
        if (type.equals("E")) {
            typeKr = "EPS";
        } else if (type.equals("G")) {
            typeKr = "그라스울";
        } else if (type.equals("W")) {
            typeKr = "우레탄";
        }
        if (subType.equals("E1")) {
            subTypeKr = "비난연";
        } else if (subType.equals("E2")) {
            subTypeKr = "난연";
        } else if (subType.equals("E3")) {
            subTypeKr = "가등급";
        } else if (subType.equals("G1")) {
            subTypeKr = "48K";
        } else if (subType.equals("G2")) {
            subTypeKr = "64K";
        } else if (subType.equals("W1")) {
            subTypeKr = "난연";
        }
        return typeKr + "," + subTypeKr + ",";
    }

    public static void _oatCal() {
        long r = 0L;
        long m = (sh % 1000);
        if (m != 0L)
            m = (1000 - sh % 1000);
        long l = (long)Math.ceil((st - m) / 1000.0D);
        for (int i = 0; i < l; i++)
            r += (long)Math.ceil(((st - m - (i * 1000)) * sw) / 2.0D / st);
        _b = r * 4L * 1000L;
    }

    public static double _comp(String _t) {
        double _COMP = 0.0D;
        if (_t.equals("cb") || _t.equals("sw") || _t.equals("b") || _t.equals("w")) {
            _COMP = 2950.0D;
        } else if (_t.equals("wb") || _t.equals("wc")) {
            _COMP = 900.0D;
        } else if (_t.equals("rls")) {
            _COMP = 1500.0D;
        } else if (_t.equals("rc")) {
            _COMP = 150.0D;
        } else {
            _COMP = 2950.0D;
        }
        return _COMP;
    }

    public static void skcal(String _t) {
        int sc = 0;
        if (sh > 6000) {
            int _r = 0;
            if (sh % 6000 == 0)
                _r = 1;
            if (_t.equals("s")) {
                _RAS_S = ((long)Math.floor((sh / 6000)) - _r) * (ge + gei);
            } else {
                _RAS_S = ((long)Math.floor((sh / 6000)) - _r) * ge;
            }
        } else {
            _RAS_S = 0L;
        }
    }

    public static void _rasCal(String type) {
        _RAS_RL = (long)Math.ceil((sh * ge));
        if (type.equals("AB") || type.equals("BB")) {
            _RAS_RL2 = ge;
        } else if (type.equals("SL")) {
            _RAS_RL2 = (ge + gei);
        } else if (type.equals("BE")) {
            _RAS_RL = (long)Math.ceil(((sh + sb) * ge));
        } else if (type.equals("AE")) {
            ge = 0;
            _RAS_RL = 0L;
        }
    }

    public static void _rasCalWc(String type) {
        _RAS_WC = 0L;
        if (type.equals("AB"))
            _RAS_WC = (long)Math.ceil(si / 900.0D) * 2L;
    }

    public static void _oasCalCb() {
        _OAS_CB = (long)Math.ceil((((si + sw) * 2) - _DAS_W) / 2900.0D);
    }

    public static void _naCal(List<InsideWall> insideWallList) {
        long _t = 0L;
        _NAS_UB = 0L;
        for (int i = 0; i < insideWallList.size(); i++) {
            int w = ((InsideWall)insideWallList.get(i)).getLength();
            int h = ((InsideWall)insideWallList.get(i)).getHeight();
            int e = ((InsideWall)insideWallList.get(i)).getAmount();
            _t += (w * h * e);
            _NAS_UB = (long)(_NAS_UB + (Math.ceil(w / 2980.0D) * 2.0D + Math.ceil(h / 2980.0D) * 2.0D) * e);
        }
        _NA = _t;
    }

    public static void _caCal(List<Ceiling> ceilingList) {
        long _t = 0L;
        for (int i = 0; i < ceilingList.size(); i++) {
            int w = ((Ceiling)ceilingList.get(i)).getLength();
            int h = ((Ceiling)ceilingList.get(i)).getHeight();
            int e = ((Ceiling)ceilingList.get(i)).getAmount();
            _t += (w * h * e);
        }
        _CA = _t;
    }

    public static void _cnaCal(List<Canopy> canopyList) {
        long _t = 0L;
        _CNAS_S1 = 0L;
        _CNAS_S2 = 0L;
        _CNAS_S3 = 0L;
        _CNAS_SW = 0L;
        for (int i = 0; i < canopyList.size(); i++) {
            int w = ((Canopy)canopyList.get(i)).getLength();
            int e = ((Canopy)canopyList.get(i)).getAmount();
            _t += (w * 2 * e);
            _CNAS_S1 += (long)(Math.floor((w / 1000)) + 1.0D) * e;
            _CNAS_S2 += (long)Math.ceil(w / 2980.0D) * e;
            _CNAS_S3 += (1 * e);
            _CNAS_SW += (long)Math.ceil(w / 2980.0D) * e;
        }
        _CNA = _t;
    }

    public static void _daCal(List<Door> doorList) {
        long _t = 0L, _w = 0L;
        for (int i = 0; i < doorList.size(); i++) {
            int w = ((Door)doorList.get(i)).getWidth();
            int h = ((Door)doorList.get(i)).getHeight();
            int e = ((Door)doorList.get(i)).getAmount();
            String t = ((Door)doorList.get(i)).getType();
            if (t.equals("O")) {
                _t = (long)(_t + w * Math.floor((h / 1000)) * 1000.0D * e);
                _w += (w * e);
            }
        }
        _DA = _t;
        _DAS_W = _w;
    }

    public static void _paCal(String _t) {
        long r = 0L, s = 0L;
        long l = (long)Math.ceil(st / 1000.0D);
        for (int i = 0; i < l; i++)
            r = (long)(r + Math.ceil((st - i * 1000.0D) * sw / 2.0D / st));
        if (_t.equals("AB")) {
            s = 2L;
            _OAS_OC = (long)(_OAS_OC + Math.ceil((st * 2) / 2950.0D) * 2.0D);
        }
        _PA = r * 4L * 1000L + si * (long)Math.ceil(st / 1000.0D) * 1000L * s;
    }

    public static double _rtCal(double _w, double _h, double _t, boolean overflow) {
        double m = 0.0D, r = 0.0D;
        if (overflow && _h % 1000.0D != 0.0D)
            m = 1000.0D - _h % 1000.0D;
        double l = (long)Math.ceil((_t - m) / 1000.0D);
        for (int i = 0; i < l; i++)
            r += (long)Math.ceil((_t - m - (i * 1000)) * _w / _t);
        return r * 1000.0D;
    }

    public static void _waCal(List<Window> windowList) {
        long _t = 0L;
        for (int i = 0; i < windowList.size(); i++) {
            int w = ((Window)windowList.get(i)).getWidth();
            int h = ((Window)windowList.get(i)).getHeight();
            int e = ((Window)windowList.get(i)).getAmount();
            _t = (long)(_t + w * Math.floor((h / 1000)) * 1000.0D * e);
        }
        _WA = _t;
    }

    public static void _kaCal(String _t) {
        _KA = 0L;
        if (_t.equals("AB")) {
            _KA = (si * 2 * 1000);
        } else if (_t.equals("AG")) {
            _KA = ((sw + si) * 1000);
        } else if (_t.equals("BBX")) {
            _KA = (si * 1000);
        }
    }

    public static void _etcCal() {
        _AA = (long)Math.ceil(_OA / 1000000.0D) + (long)Math.ceil(_RA / 1000000.0D) + (long)Math.ceil(_CA / 1000000.0D) + (long)Math.ceil(_NA / 1000000.0D) + (long)Math.ceil(_PA / 1000000.0D) + (long)Math.ceil(_CNA / 1000.0D) + (long)Math.ceil(_KA / 1000000.0D);
        _EAS_S1 = _AA * 1500L;
        _EAS_S1_UC = 1500L;
        if (sh < 8000) {
            _EAS_S2 = _AA * 2500L;
            _EAS_S2_UC = 2500L;
        } else {
            _EAS_S2 = _AA * 3000L;
            _EAS_S2_UC = 3000L;
        }
        if (oomk.equals("E")) {
            _EAS_S3 = _AA * 1500L;
            _EAS_S3_UC = 1500L;
        } else {
            _EAS_S3 = _AA * 2000L;
            _EAS_S3_UC = 2000L;
        }
    }
}
