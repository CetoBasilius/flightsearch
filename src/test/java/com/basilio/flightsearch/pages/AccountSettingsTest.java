package com.basilio.flightsearch.pages;


import com.basilio.flightsearch.dal.persistence.ServiceDAO;
import com.basilio.flightsearch.entities.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;

/**
 * Created with IntelliJ IDEA.
 * User: bgerman
 * Date: 7/31/12
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountSettingsTest {

    @Test
    public void testGetUserlist() throws Exception {
        AccountSettings accountSettings = new AccountSettings();

        List<Object> aUserList = new ArrayList<Object>();
        aUserList.add(new User());
        aUserList.add(new User());

        ServiceDAO mockedDAO = createNiceMock(ServiceDAO.class);
        accountSettings.setServiceDAO(mockedDAO);

        expect(mockedDAO.findWithNamedQuery(User.ALL)).andReturn(aUserList).once();

        replay(mockedDAO);

        accountSettings.getUserlist();

        verify(mockedDAO);
    }
}
