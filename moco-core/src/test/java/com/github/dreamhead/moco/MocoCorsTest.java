package com.github.dreamhead.moco;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.junit.Test;

import static com.github.dreamhead.moco.Moco.allowHeaders;
import static com.github.dreamhead.moco.Moco.allowMethods;
import static com.github.dreamhead.moco.Moco.allowOrigin;
import static com.github.dreamhead.moco.Moco.cors;
import static com.github.dreamhead.moco.Runner.running;
import static com.github.dreamhead.moco.helper.RemoteTestUtils.root;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MocoCorsTest extends AbstractMocoHttpTest {
    @Test
    public void should_support_cors() throws Exception {
        server.response(cors());

        running(server, () -> {
            ClassicHttpResponse response = helper.getResponse(root());
            assertThat(response.getHeader("Access-Control-Allow-Origin").getValue(), is("*"));
        });
    }

    @Test
    public void should_support_cors_with_origin() throws Exception {
        server.response(cors(allowOrigin("foo")));

        running(server, () -> {
            ClassicHttpResponse response = helper.getResponse(root());
            assertThat(response.getHeader("Access-Control-Allow-Origin").getValue(), is("foo"));
        });
    }

    @Test
    public void should_support_cors_with_methods() throws Exception {
        server.response(cors(allowOrigin("foo"), allowMethods("GET")));

        running(server, () -> {
            ClassicHttpResponse response = helper.getResponse(root());
            assertThat(response.getHeader("Access-Control-Allow-Methods").getValue(), is("GET"));
        });
    }

    @Test
    public void should_support_cors_with_headers() throws Exception {
        server.response(cors(allowHeaders("X-Header")));

        running(server, () -> {
            ClassicHttpResponse response = helper.getResponse(root());
            assertThat(response.getHeader("Access-Control-Allow-Headers").getValue(), is("X-Header"));
        });
    }

    @Test
    public void should_support_cors_with_origin_methods_and_headers() throws Exception {
        server.response(cors(allowOrigin("foo"), allowMethods("GET"), allowHeaders("X-Header")));

        running(server, () -> {
            ClassicHttpResponse response = helper.getResponse(root());
            assertThat(response.getHeader("Access-Control-Allow-Origin").getValue(), is("foo"));
            assertThat(response.getHeader("Access-Control-Allow-Methods").getValue(), is("GET"));
            assertThat(response.getHeader("Access-Control-Allow-Headers").getValue(), is("X-Header"));
        });
    }
}
