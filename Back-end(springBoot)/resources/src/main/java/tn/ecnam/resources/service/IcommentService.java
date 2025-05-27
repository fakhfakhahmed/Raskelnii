package tn.ecnam.resources.service;

import tn.ecnam.resources.entity.Blog;
import tn.ecnam.resources.entity.Comments;

public interface IcommentService {
    Comments GetComment(String CommentId);

    void save(Comments existingComment);
}
