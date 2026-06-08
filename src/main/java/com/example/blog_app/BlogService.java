package com.example.blog_app;


import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    // ★ コンストラクタ内の変数代入ミスを修正
    public BlogService(BlogRepository blogRepository){
        this.blogRepository = blogRepository;
    }

    // 全件取得
    public List<Blog> findAll(){
        return blogRepository.findAll();
    }

    // ★ 1件取得（Controllerのdetailから呼ばれる）
    public Blog findById(Long id) {
        return blogRepository.findById(id);
    }

    // ★ 保存処理（Controllerのcreateから呼ばれる）
    public void save(String title, String content) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("タイトルが空です");
        }
        
        // 新規登録なので id は null を指定してデータベース側に採番を任せる
        Blog blog = new Blog(null, title, content);
        blogRepository.save(blog);
    }
}