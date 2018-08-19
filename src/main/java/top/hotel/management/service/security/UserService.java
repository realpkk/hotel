package top.hotel.management.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hotel.management.common.utils.MD5Util;
import top.hotel.management.entity.security.Customer;
import top.hotel.management.repository.security.CustomerRepository;

@Service
public class UserService {

    @Autowired
    private CustomerRepository customerRepository;

    public void createNewUser(Customer customer){
        customerRepository.save(customer);
    }

    public boolean duplicateCheck(String phoneNumber){
        Customer customer = customerRepository.findCustomerByPhoneNumber(phoneNumber);
        if (customer == null) {
            return false;
        }else {
            return true;
        }
    }

    public boolean loginCheck(String phoneNumber,String password){
        Customer customer = customerRepository.findCustomerByPhoneNumber(phoneNumber);
        if (MD5Util.encode(password).equals(customer.getPassword())) {
            return true;
        }else {
            return false;
        }
    }
}


