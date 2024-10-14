package com.xiaomi.xiaoai.codequality.basecode.pull;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GitlabProjectsFetcherWithPagination {
    private static final int PER_PAGE = 200;

    public static List<String> fetchAllGitlabProjects(String url, String group,String token) {

        List<String> gitLinks = new ArrayList<>();
        int page = 1;
        while (true) {
            String urlWithParams = url + "/api/v4/groups/" + group + "/projects" + "?per_page=" + PER_PAGE + "&page=" + page;
            HttpResponse response = HttpRequest.get(urlWithParams)
                    .header("PRIVATE-TOKEN", token)
                    .execute();
            if (response.getStatus() == 200) {
                JSONArray projects = new JSONArray(response.body());

                if (projects.isEmpty()) {
                    break;
                }
                for (int i = 0; i < projects.size(); i++) {
                    JSONObject project = projects.getJSONObject(i);
                    String gitUrl = project.getStr("ssh_url_to_repo");  // 获取 SSH URL
                    gitLinks.add(gitUrl);
                }
                page++;
            } else {
                throw new RuntimeException("Failed to fetch projects. Response code: " + response.getStatus());
            }
        }

        return gitLinks;
    }
}
