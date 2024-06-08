package com.fithub.services.training.test.suites;

import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;

import com.fithub.services.training.api.ClientService;
import com.fithub.services.training.core.impl.ClientServiceImpl;
import com.fithub.services.training.dao.repository.ClientRepository;
import com.fithub.services.training.dao.repository.UserRepository;
import com.fithub.services.training.test.configuration.BasicTestConfiguration;

public class ClientServiceTest extends BasicTestConfiguration {

    private UserRepository userRepository;
    private ClientRepository clientRepository;

    private ClientService clientService;

    @BeforeMethod
    public void beforeMethod() {
        userRepository = Mockito.mock(UserRepository.class);
        clientRepository = Mockito.mock(ClientRepository.class);

        clientService = new ClientServiceImpl(userRepository, clientRepository);

    }

}