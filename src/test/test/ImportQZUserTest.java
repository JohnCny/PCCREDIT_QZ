package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cardpay.pccredit.system.service.ImportQZUserData;
import com.wicresoft.jrad.modules.privilege.business.UserManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-junit.xml")
public class ImportQZUserTest {

	@Autowired
	private ImportQZUserData importQZUserData;
	
	@Test
	public void test() {
		try {
			importQZUserData.insertUser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
