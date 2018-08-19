package top.hotel.management.repository.security;

import top.hotel.management.entity.security.Customer;
import top.hotel.management.repository.base.AbstractRepository;

public interface CustomerRepository extends AbstractRepository<Customer,Long> {

    Customer findCustomerByPhoneNumber(String phoneNumber);
}
