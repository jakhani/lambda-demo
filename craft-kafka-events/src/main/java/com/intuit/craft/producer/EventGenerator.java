package com.intuit.craft.producer;
import Signup.Trial;
import Signup.Message_facts;
import Signup.Message_dims;
import com.intuit.craft.utils.CommonUtil;
import java.util.Random;

public class EventGenerator {
  private static final String[] COUNTRIES = {"US", "India", "Spain", "China", "Canada"};
  private static final String[] CHANNEL = {"Web", "Mobile", "Desktop"};
  private static final String[] ITEM = {"QuicksbookOnline","Mint","Turbo-tax"};
  private static final String[] CARD_TYPE = {"Visa", "Master", "Discover"};
  private static final double[] PRODUCT_PRICE = {20, 25, 30};
  private static long COMPANY_ID = 100001;
  private static final String COMPANY_ID_PREFIX = "icd";

  public static Trial getNext() {

    long currentTime = CommonUtil.getCurrentTime();
    long expirationTime = CommonUtil.getTimeAfterMonth();
    Random r = new Random();
    Trial trial = new Trial();
    trial.setCountry(COUNTRIES[r.nextInt(COUNTRIES.length)]);
    trial.setEventTimestamp(currentTime);
    String companyId = "icd" + String.valueOf(COMPANY_ID++);
    trial.setCompanyId(companyId);
    trial.setMessageFacts(generateMessageFacts(currentTime, expirationTime, companyId));
    trial.setMessageDims(generateMessageDims());
    return trial;
  }


  private static Message_dims generateMessageDims() {
    Random r = new Random();
    Message_dims dims = new Message_dims();
    dims.setChannel(CHANNEL[r.nextInt(CHANNEL.length)]);
    dims.setItem(ITEM[r.nextInt(ITEM.length)]);
    return dims;
  }

  private static Message_facts generateMessageFacts(long currentTime, long expirationTime, String companyId) {
    Random r = new Random();
    Message_facts facts = new Message_facts();
    facts.setQuantity(r.nextInt(20));
    facts.setProductPrice(PRODUCT_PRICE[r.nextInt(PRODUCT_PRICE.length)]);
    facts.setCurrency("USD");
    facts.setExecutionTs(currentTime);
    facts.setExpirationTs(expirationTime);
    facts.setCreditCardType(CARD_TYPE[r.nextInt(CARD_TYPE.length)]);
    facts.setCompanyEmail("xyz@" + companyId + ".com");
    return facts;
  }
}
