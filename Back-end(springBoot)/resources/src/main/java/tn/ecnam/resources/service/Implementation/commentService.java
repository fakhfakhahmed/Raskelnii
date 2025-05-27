package tn.ecnam.resources.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.ecnam.resources.entity.Comments;
import tn.ecnam.resources.repository.BlogRepository;
import tn.ecnam.resources.repository.CommentRepository;
import tn.ecnam.resources.service.IcommentService;

@Service
public class commentService implements IcommentService {


 @Autowired
 private CommentRepository cr;
    @Override
    public Comments GetComment(String CommentId) {
        return cr.findById(CommentId).orElse(null);
    }

    @Override
    public void save(Comments existingComment) {
        cr.save(existingComment);
    }
}
