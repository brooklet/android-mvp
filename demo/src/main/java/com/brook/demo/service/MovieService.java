package com.brook.demo.service;

import androidx.annotation.Keep;
import com.brook.demo.bean.MovieList;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

@Keep
public interface MovieService {

    @GET("/j/search_subjects")
    Observable<MovieList> searchSubjects(
            @Query("type") String type,
            @Query("tag") String tag,
            @Query("page_limit") int page_limit,
            @Query("page_start") int page_start);

}
