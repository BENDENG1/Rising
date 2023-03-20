package com.softsquared.template.kotlin.src.main.Story.models

data class SpecificStoryResult(
    val created_at: String,
    val nickname: String,
    val profile_image_url: String,
    val story_url: String,
    val story_viewer_count: Int,
    val story_viewer_profile_image_urls: List<String>,
    val updated_at: String,
    val user_id: Int,
    val user_story_id: Int
)