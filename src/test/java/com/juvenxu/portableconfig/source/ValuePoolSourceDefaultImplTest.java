package com.juvenxu.portableconfig.source;

import com.juvenxu.portableconfig.ValuePoolSource;
import com.juvenxu.portableconfig.model.ValuePool;
import org.codehaus.plexus.PlexusTestCase;

import javax.mail.util.ByteArrayDataSource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author juven
 */
public class ValuePoolSourceDefaultImplTest extends PlexusTestCase
{

  private ValuePoolSource sut;

  @Override
  public void setUp() throws Exception
  {
    sut = lookup(ValuePoolSource.class, "default");
  }

  public void testCommentShouldBeIgnored() throws Exception
  {
    String text = "#key1=value1\n";
    ValuePool result = sut.load(new ByteArrayDataSource(text, null));

    assertThat(result.get("key1"), nullValue());
  }


  public void testLoadKeyValueWithoutSpaceAroundSeparator() throws Exception
  {
    String text = "key1=value1\n" +
            "key2=value2";

    ValuePool result = sut.load(new ByteArrayDataSource(text, null));

    assertThat(result.get("key1"), equalTo("value1"));
    assertThat(result.get("key2"), equalTo("value2"));
  }

  public void testLoadKeyValueWithSpaceAroundSeparator() throws Exception
  {
    String text = "key1 =  value1\n" +
            "key2 =   value2";

    ValuePool result = sut.load(new ByteArrayDataSource(text, null));

    assertThat(result.get("key1"), equalTo("value1"));
    assertThat(result.get("key2"), equalTo("value2"));
  }
}
