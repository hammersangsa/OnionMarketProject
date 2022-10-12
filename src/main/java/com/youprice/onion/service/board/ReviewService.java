package com.youprice.onion.service.board;

import com.youprice.onion.dto.board.ReviewDTO;
import com.youprice.onion.dto.board.ReviewFormDTO;
import com.youprice.onion.entity.board.ReviewImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReviewService {
    ReviewDTO getReviewDTO(Long reviewId);
    Long saveReview(ReviewFormDTO form, List<MultipartFile> reviewImageName) throws IOException;
    ReviewDTO findByUserId(String userId);
    public ReviewDTO findReviewDTO(Long id);
    public List<ReviewDTO> findAll();

    // 사진
    List<ReviewImage> storeImages(Long reviewId, List<MultipartFile> multipartFiles) throws IOException;
    String storePath(MultipartFile multipartFile) throws IOException;
}
