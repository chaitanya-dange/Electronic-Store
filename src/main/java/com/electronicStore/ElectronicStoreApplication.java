package com.electronicStore;

import com.electronicStore.entities.Role;
import com.electronicStore.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class ElectronicStoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	@Value("${admin.role.id}")
	private String role_admin_id;

	@Value("${normal.role.id}")
	private String role_normal_id;


	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("abcd"));
		 try {
			 Role roleAdmin = Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
			 Role roleNormal = Role.builder().roleId(role_normal_id).roleName("ROLE_NORMAL").build();

			 roleRepository.save(roleAdmin);
			 roleRepository.save(roleNormal);

		 }catch (Exception e){
			 e.printStackTrace();

		 }
	}
}
