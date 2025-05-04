package jungjin;

import com.github.jknack.handlebars.Options;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaginationHelper {
    private static final Logger logger = LoggerFactory.getLogger(PaginationHelper.class);

    public static final String HELPER_NAME = "pagination";

    public CharSequence pagination(Object context, Options options) throws IOException {
        Map<String, Object> paginationInfoMap;
        try {
            int currentPageNumber = ((Integer)options.param(0, Integer.valueOf(1))).intValue();
            int totalPageCount = ((Integer)options.param(1, Integer.valueOf(1))).intValue();
            int pageGroupCount = ((Integer)options.param(2, Integer.valueOf(10))).intValue();
            int firstPageIdx = (currentPageNumber - 1) / pageGroupCount * pageGroupCount + 1;
            int lastPageIdx = (currentPageNumber - 1) / pageGroupCount * pageGroupCount + pageGroupCount;
            int previousIdx = lastPageIdx - pageGroupCount;
            int nextIdx = lastPageIdx + 1;
            boolean canGoPrevious = (firstPageIdx > 1);
            boolean canGoNext = (totalPageCount > lastPageIdx);
            int displayedLastPage = (totalPageCount < lastPageIdx) ? totalPageCount : lastPageIdx;
            paginationInfoMap = makePaginationInfoMap(canGoPrevious, canGoNext, currentPageNumber, firstPageIdx, displayedLastPage, previousIdx, nextIdx);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            paginationInfoMap = Maps.newHashMap();
        }
        return options.fn(paginationInfoMap);
    }

    private Map<String, Object> makePaginationInfoMap(boolean canGoPrevious, boolean canGoNext, int page, int firstPage, int displayedLastPage, int previousIdx, int nextIdx) {
        Map<String, Object> paginationInfoMap = Maps.newHashMap();
        List<Map> pageList = Lists.newArrayList();
        for (int i = firstPage; i <= displayedLastPage; i++) {
            Map<String, Object> numberMap = Maps.newHashMap();
            numberMap.put("page", String.valueOf(i));
            numberMap.put("isCurrent", Boolean.valueOf((i == page)));
            pageList.add(numberMap);
        }
        paginationInfoMap.put("canGoPrevious", Boolean.valueOf(canGoPrevious));
        paginationInfoMap.put("previousIdx", Integer.valueOf(previousIdx));
        paginationInfoMap.put("pages", pageList);
        paginationInfoMap.put("pagesSize", Integer.valueOf(pageList.size()));
        paginationInfoMap.put("displayedLastPage", Integer.valueOf(displayedLastPage));
        paginationInfoMap.put("canGoNext", Boolean.valueOf(canGoNext));
        paginationInfoMap.put("nextIdx", Integer.valueOf(nextIdx));
        return paginationInfoMap;
    }
}
