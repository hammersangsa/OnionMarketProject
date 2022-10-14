package com.youprice.onion.controller.order;

import com.youprice.onion.dto.member.MemberDTO;
import com.youprice.onion.dto.member.SessionDTO;
import com.youprice.onion.dto.order.WishDTO;
import com.youprice.onion.security.auth.LoginUser;
import com.youprice.onion.service.order.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("wish")
public class WishController {

    private final WishService wishService;
	private final HttpSession session;

    // 찜 목록 페이지
    @GetMapping("wishList")
    public String wishList(@LoginUser SessionDTO sessionDTO, Model model) {
		if (sessionDTO == null) return "redirect:/member/login";

		List<WishDTO> list = wishService.getWishList(sessionDTO.getId());

		model.addAttribute("list", list);
        return "wish/wishList";
    }

    // 찜 추가
    @GetMapping("addWish")
    public String addWish(@LoginUser SessionDTO sessionDTO, Long productId) {
		if (sessionDTO == null) return "redirect:/member/login";

		wishService.addWish(sessionDTO.getId(), productId);

        return "redirect:/wish/wishList";
    }

    // 찜 삭제
    @PostMapping("removeWish")
    public String removeWish(Long wishId) {

		wishService.removeWish(wishId);

        return "redirect:/wish/wishList";
    }

}
