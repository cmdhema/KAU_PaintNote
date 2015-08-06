package kau.paintnote;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import kau.paintnote.model.LineModel;
import kau.paintnote.model.ResponseModel;
import kau.paintnote.model.UserModel;

/**
 * Created by Jaewook on 2015-06-22.
 */
//@Rest(converters = {MappingJackson2HttpMessageConverter.class})
public interface MyRestClient {

//    void setRootUrl(String rootUrl);
//
//    @Get("/note/{userId}")
//    @Accept(MediaType.APPLICATION_JSON)
//    LineModel getNotes(int userId);
//
//    @Post("/user/login")
//    @Accept(MediaType.APPLICATION_JSON)
//    ResponseModel doLogin(UserModel user);

}
