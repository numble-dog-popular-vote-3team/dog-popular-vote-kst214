package com.vote.dog.dogvote.controller;

import com.vote.dog.dogvote.dto.Dog;
import com.vote.dog.dogvote.dto.RestResponsePage;
import com.vote.dog.dogvote.entity.DogEntity;
import com.vote.dog.dogvote.repository.DogRepository;
import com.vote.dog.dogvote.service.DogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * com.vote.dog.dogvote.controller
 * ㄴ TestController
 *
 * <pre>
 * description :
 * </pre>
 *
 * <pre>
 * <b>History:</b>
 *  george, 1.0, 2023/05/18  초기작성
 * </pre>
 *
 * @author george
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
public class DogController
{
  private final DogService dogService;

  @GetMapping("/dog")
  public String list(Model model,
                     @RequestParam(value = "type", required = false, defaultValue = "") String type,
                     @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                     @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                     @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                     @RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy
  )

  {
    // List<Dog> dogList = dogService.list();
    RestResponsePage<Dog> list = dogService.list(model, type, keyword, page, size ,orderBy);


    model.addAttribute("dogs", list);

    return "dog/list";
  }

  @GetMapping("/detail/{id}")
  public String detail(Model model,@PathVariable Long id)
  {
    Dog dog= dogService.detail(id);


    model.addAttribute("dog", dog);

    return "dog/detail";
  }

  @ResponseBody
  @GetMapping("/dog1")
  public void list1(Model model)
  {
    dogService.saveDog();;
  }
}
