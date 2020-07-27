package ge.rrs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Template controller class.
 */
@Controller
public class RenderListView {

    @RequestMapping("/homepage-list-view")
    public ModelAndView renderListView() {
        ModelAndView mv = new ModelAndView();

        // TODO: USER CODE START

        // for reference
        // mv.addObject("attrName", objName);

        // TODO: USER CODE END

        mv.setViewName("/homepage-list-view");

        return mv;
    }

    /**
     * Do not change this.
     */
    @RequestMapping("/filter")
    public ModelAndView filterRooms() {
        return renderListView();
    }
}
