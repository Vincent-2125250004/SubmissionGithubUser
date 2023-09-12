package com.dicoding.submissiongithubuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithubuser.adapter.FollowersAdapter
import com.dicoding.submissiongithubuser.adapter.FollowingAdapter
import com.dicoding.submissiongithubuser.api.response.ItemsItem
import com.dicoding.submissiongithubuser.databinding.FragmentFollowBinding
import com.dicoding.submissiongithubuser.ui.viewmodel.MainViewModel

class FollowFragment : Fragment() {
    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "varUsername"
    }

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private var position: Int = 0
    private var username: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]


        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        if (position == 1) {
            val layoutManager = LinearLayoutManager(requireContext())
            binding.rvFollowers.layoutManager = layoutManager
            username?.let {
                mainViewModel.getFollowers(it)
                mainViewModel.listFollowers.observe(requireActivity()) {
                    setFollowers(it)
                }

                mainViewModel.isLoading.observe(requireActivity()) {
                    showLoading(it)
                }

            }
        } else {
            val layoutManager = LinearLayoutManager(requireContext())
            binding.rvFollowing.layoutManager = layoutManager
            username?.let {
                mainViewModel.getFollowing(it)
                mainViewModel.listFollowing.observe(requireActivity()) {
                    setFollowing(it)
                }

                mainViewModel.isLoading.observe(requireActivity()) {
                    showLoading(it)
                }

            }

        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    fun setFollowing(following: List<ItemsItem>) {
        val adapter = FollowingAdapter()
        adapter.submitList(following)
        binding.rvFollowing.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun setFollowers(followers: List<ItemsItem>) {
        val adapter = FollowersAdapter()
        adapter.submitList(followers)
        binding.rvFollowers.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}