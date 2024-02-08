package com.notation.campusnote.setting

import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.notation.campusnote.R
import com.notation.campusnote.databinding.ActivitySettingBinding
import java.util.Locale


class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)

        val preferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val savedLanguage = preferences.getString("language", "ko") ?: "ko" // 기본값은 "ko"
        setLanguageTags(savedLanguage)

        binding.settingProfileNextIv.setOnClickListener {
            // 계정관리로 이동
        }

        binding.settingAutoRecodeOffIv.setOnClickListener {
            binding.settingAutoRecodeOffIv.visibility = View.GONE
            binding.settingAutoRecodeOnIv.visibility = View.VISIBLE
            // 녹음 설정 코드
        }
        binding.settingAutoRecodeOnIv.setOnClickListener {
            binding.settingAutoRecodeOffIv.visibility = View.VISIBLE
            binding.settingAutoRecodeOnIv.visibility = View.GONE
            // 녹음 설정 코드
        }
        binding.settingAlarmOffIv.setOnClickListener {
            binding.settingAlarmOffIv.visibility = View.GONE
            binding.settingAlarmOnIv.visibility = View.VISIBLE
            // 알람 설정 코드
        }
        binding.settingAlarmOnIv.setOnClickListener {
            binding.settingAlarmOffIv.visibility = View.VISIBLE
            binding.settingAlarmOnIv.visibility = View.GONE
            // 알람 설정 코드
        }
        binding.settingLanguageNextIv.setOnClickListener {
            Log.d("click", "Setting Language Next Clicked")
            showLanguageMenu(it)//언어 변경 기능 구현해보다가 막힌 상태
        }
        binding.settingSubscribeNextIv.setOnClickListener {
            // 구독 설정 코드
        }
        binding.settingDirectoryNextIv.setOnClickListener {
            // 녹음파일 경로 설정 코드
        }
        binding.settingBackBtn.setOnClickListener {
            finish()
        }

        setContentView(binding.root)
    }

    private fun showLanguageMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.language_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_korean -> {
                    binding.settingLanguageSelectEnglishTv.visibility = View.GONE
                    binding.settingLanguageSelectKoreanTv.visibility = View.VISIBLE
                    setLanguageTags("ko")
                    true
                }
                R.id.menu_english -> {
                    binding.settingLanguageSelectKoreanTv.visibility = View.GONE
                    binding.settingLanguageSelectEnglishTv.visibility = View.VISIBLE
                    setLanguageTags("en")
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun setLanguageTags(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        saveLanguagePreference(languageCode)

        val resources: Resources = resources
        val configuration = resources.configuration

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }

        resources.updateConfiguration(configuration, resources.displayMetrics)

        // 액티비티를 재시작하지 않고 언어 설정을 적용합니다.
        // 이 부분만 수정되도록 하기 위해서 레이아웃 변경을 막습니다.
        binding = ActivitySettingBinding.bind(binding.root)

        // 여기서 필요한 언어에 따른 뷰 초기화 코드를 추가할 수 있습니다.
        // 예: binding.settingLanguageSelectKoreanTv.visibility = if (languageCode == "ko") View.VISIBLE else View.GONE
    }



    private fun saveLanguagePreference(languageCode: String) {
        val preferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("language", languageCode)
        editor.apply()
    }
}