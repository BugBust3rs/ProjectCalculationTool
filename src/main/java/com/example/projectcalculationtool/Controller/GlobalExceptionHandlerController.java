package com.example.projectcalculationtool.Controller;

import com.example.projectcalculationtool.Exceptions.MemberAlreadyAddedException;
import com.example.projectcalculationtool.Exceptions.NotLoggedInException;
import com.example.projectcalculationtool.Exceptions.UnauthorizedAccessException;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleUnauthorized(UnauthorizedAccessException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/unauthorized";
    }


    @ExceptionHandler(NotLoggedInException.class)
    public String handleNotLoggedIn(NotLoggedInException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("sessionExpiredMsg", ex.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(MemberAlreadyAddedException.class)
    public String handleMemberAlreadyAddedException(MemberAlreadyAddedException ex, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("memberAlreadyAddedMsg", ex.getMessage());
        return "redirect:/taskOverview/" + ex.getProjectId();
    }

    @ExceptionHandler(DataAccessException.class)
    public String handleDataAccessException(DataAccessException ex, Model model){
        model.addAttribute("errorMessage", ex.getCause());
        return "error/500";
    }
}
