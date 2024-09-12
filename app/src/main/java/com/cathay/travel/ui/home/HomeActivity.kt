package com.cathay.travel.ui.home

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.cathay.travel.R
import com.cathay.travel.databinding.ActivityHomeBinding
import com.cathay.travel.model.lang.Language
import com.cathay.travel.utils.LanguageUtil
import com.cathay.travel.utils.SharedPreferenceUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private var langIcon: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 綁定客製化 ActionBar
        setSupportActionBar(binding.toolbar)

        // 設定 Navigation host fragment
        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // 切換語言時，設定語言 icon 只在首頁顯示
            when (destination.id) {
                R.id.HomeFragment -> langIcon?.apply { isVisible = true }
                else -> langIcon?.apply { isVisible = false }
            }
        }

        // ActionBar 綁定 Navigation graph
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    /**
     * 更換語言
     */
    private fun changeLanguage(languageCode: String) {
        SharedPreferenceUtil.setLanguageCode(this, languageCode)
        recreate()
    }

    /**
     * 顯示選擇語言視窗
     */
    private fun showLanguageDialog() {
        val currentLangCode = SharedPreferenceUtil.getLanguageCode(this)
        val langItemList = LanguageUtil.langList
        val languageList = langItemList.map { it.name }.toTypedArray()
        val checkIndex = langItemList.indexOf(
            langItemList.find { it.langTag == currentLangCode }
        )
        AlertDialog.Builder(this).setSingleChoiceItems(languageList, checkIndex) { dialog, which ->
            val selectedLangTag = when(which) {
                0 -> Language.ZhTw.langTag
                1 -> Language.ZhCn.langTag
                2 -> Language.En.langTag
                3 -> Language.Ja.langTag
                4 -> Language.Ko.langTag
                5 -> Language.Es.langTag
                6 -> Language.Id.langTag
                7 -> Language.Th.langTag
                8 -> Language.Vi.langTag
                else -> Language.ZhTw.langTag
            }
            changeLanguage(selectedLangTag)
            dialog.dismiss()
        }.setTitle(R.string.label_choose_language).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        langIcon = menu?.findItem(R.id.action_lang)
        langIcon?.setOnMenuItemClickListener {
            showLanguageDialog()
            true
        }
        // 設定語言 icon 只在首頁顯示
        langIcon?.isVisible =
            findNavController(R.id.nav_host_fragment).currentDestination?.id == R.id.HomeFragment
        return true
    }

    override fun attachBaseContext(newBase: Context?) {
        // 依照 SharedPreference 中儲存的語言設定 resource config
        newBase?.let { context ->
            val langCode = SharedPreferenceUtil.getLanguageCode(context)
            langCode?.let {
                LanguageUtil.setLocale(context, it)
            }
        }
        super.attachBaseContext(newBase)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}