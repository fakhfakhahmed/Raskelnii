package tn.ecnam.resources.service;

import org.springframework.scheduling.annotation.Scheduled;
import tn.ecnam.resources.entity.Reedemcode;

public interface IRedeemCodeService {

    void cleanupExpiredRedeemCodes();

}
