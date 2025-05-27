package tn.ecnam.resources.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.ecnam.resources.entity.Reedemcode;
import tn.ecnam.resources.repository.RedeemcodeRepository;
import tn.ecnam.resources.service.IRedeemCodeService;

import java.util.Date;
import java.util.List;
@Service
public class redeemCodeService implements IRedeemCodeService {

    @Autowired
    RedeemcodeRepository redeemcodeRepository;
    @Scheduled(cron = "0 0 * * * *") // Run daily at midnight (00:00)
    @Override
    public void cleanupExpiredRedeemCodes() {
        System.out.println("Cleanup method executed at: " + new Date());
        Date twoDaysAgo = new Date(System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000)); // Calculate the date 2 days ago
        // Find and delete all expired codes
        List<Reedemcode> expiredCodes = redeemcodeRepository.findByCreatedTimeBefore(twoDaysAgo);
        for (Reedemcode code : expiredCodes) {
            redeemcodeRepository.delete(code);
        }
    }


}
