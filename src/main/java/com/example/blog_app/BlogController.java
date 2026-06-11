package com.example.blog_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/")
    public String blog(Model model) {

        model.addAttribute("blogs", blogService.findAll());

        return "blog";
    }

    @GetMapping("/blogs/{id}")
    public String detail(
            @PathVariable Long id,
            Model model) {

        model.addAttribute("blog", blogService.findById(id));

        return "blogs/detail";
    }

    @PostMapping("/blogs")
    public String create(
            @RequestParam String title,
            @RequestParam String content) {

        blogService.save(title, content);

        return "redirect:/";
    }

    @GetMapping("/blogs/new")
    public String newBlog() {
        return "blogs/new";
    }
}