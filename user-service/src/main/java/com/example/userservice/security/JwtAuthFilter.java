package com.example.userservice.security;

                import jakarta.servlet.FilterChain;
                import jakarta.servlet.ServletException;
                import jakarta.servlet.http.HttpServletRequest;
                import jakarta.servlet.http.HttpServletResponse;
                import org.springframework.security.core.userdetails.UserDetailsService;
                import org.springframework.stereotype.Component;
                import org.springframework.web.filter.OncePerRequestFilter;

                import java.io.IOException;

                @Component
                public class JwtAuthFilter extends OncePerRequestFilter {
                    private final UserDetailsService userDetailsService;

                    public JwtAuthFilter(UserDetailsService userDetailsService) {
                        this.userDetailsService = userDetailsService;
                    }

                    @Override
                    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                            throws ServletException, IOException {
                        // No JWT validation, just continue the filter chain
                        filterChain.doFilter(request, response);
                    }
                }