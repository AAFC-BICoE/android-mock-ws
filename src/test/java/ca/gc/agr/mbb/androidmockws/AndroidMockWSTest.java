package ca.gc.agr.mbb.androidmockws;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AndroidMockWSTest
{
    AndroidMockWS service = null;

    @Before
    public void init()
    {
	service = new AndroidMockWS();
	service.start();
    }

}
