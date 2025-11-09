package com.example.coletaplus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UserHistoryBottomSheet : BottomSheetDialogFragment() {

    private var userName: String? = null
    private var userPoints: String? = null

    companion object {
        const val TAG = "UserHistoryBottomSheet"
        private const val ARG_USER_NAME = "user_name"
        private const val ARG_USER_POINTS = "user_points"

        fun newInstance(name: String, points: String): UserHistoryBottomSheet {
            val fragment = UserHistoryBottomSheet()
            val args = Bundle()
            args.putString(ARG_USER_NAME, name)
            args.putString(ARG_USER_POINTS, points)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userName = it.getString(ARG_USER_NAME)
            userPoints = it.getString(ARG_USER_POINTS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_user_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.ivClose).setOnClickListener { dismiss() }

        val tvUserNameHistory = view.findViewById<TextView>(R.id.tvUserNameHistory)
        tvUserNameHistory.text = arguments?.getString(ARG_USER_NAME) ?: "Usuário"

        val rvCheckinHistory = view.findViewById<RecyclerView>(R.id.rvCheckinHistory)
        rvCheckinHistory.layoutManager = LinearLayoutManager(context)
        rvCheckinHistory.adapter = HistoryAdapter(4)
    }
}