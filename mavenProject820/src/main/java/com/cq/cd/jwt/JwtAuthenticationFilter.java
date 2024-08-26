package com.cq.cd.jwt;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//请求过滤器
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            //请求的url是否时受到保护的对象
            if(isProtectedUrl(request)) {
//                System.out.println(request.getMethod());
                if(!request.getMethod().equals("OPTIONS"))
                    request = JwtUtil.validateTokenAndAddUserIdToHeader(request);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }
    //收到保护的url，内容涵括……；不保护的内容，包括登录注册、公告信息、查看帖子、获得帖子列表
    private boolean isProtectedUrl(HttpServletRequest request) {
        List<String> protectedPaths = new ArrayList<String>();
        protectedPaths.add("/ums/user/info");//获取用户信息search
        protectedPaths.add("/ums/user/update");//更新用户信息
        //发贴、更新帖、删除帖
        protectedPaths.add("/post/create");
        protectedPaths.add("/post/update");
        protectedPaths.add("/post/delete/*");
        //提交评论
        protectedPaths.add("/comment/add_comment");
        protectedPaths.add("/relationship/subscribe/*");
        protectedPaths.add("/relationship/unsubscribe/*");
        protectedPaths.add("/relationship/validate/*");

        boolean bFind = false;
        for( String passedPath : protectedPaths ) {
            bFind = pathMatcher.match(passedPath, request.getServletPath());
            if( bFind ) {
                break;
            }
        }
        return bFind;
    }

}