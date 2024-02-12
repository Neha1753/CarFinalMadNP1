package com.example.carfinalmad

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.carfinalmad.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class home : Fragment() {

    private lateinit var mainBinding: FragmentHomeBinding
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = mainBinding.root

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        mainBinding.button1.setOnClickListener {
            getLocation()
        }

        val Button2: Button = view.findViewById(R.id.button2)
        val Button3: Button = view.findViewById(R.id.button3)
        Button2.setOnClickListener {
            val c1 = Calendar.getInstance()
            val year = c1.get(Calendar.YEAR)
            val month = c1.get(Calendar.MONTH)
            val day = c1.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, monthOfYear, dayOfMonth ->

                    val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                    val timePickerDialog = TimePickerDialog(
                        requireContext(),
                        { _, hourOfDay, minute ->

                            val selectedTime = if (hourOfDay in 8 until 20) {
                                if (hourOfDay < 12) {
                                    "$hourOfDay:$minute AM"
                                } else {
                                    val hour = if (hourOfDay > 12) hourOfDay - 12 else hourOfDay
                                    "$hour:$minute PM"
                                }
                            } else {
                                // Reset the time if selected time is not between 8 AM and 8 PM
                                Toast.makeText(requireContext(), "Please select time between 8 AM and 8 PM", Toast.LENGTH_SHORT).show()
                                return@TimePickerDialog
                            }

                            Button2.text = "$selectedDate $selectedTime"
                        },
                        c1.get(Calendar.HOUR_OF_DAY), // Corrected here
                        c1.get(Calendar.MINUTE), // Corrected here
                        false
                    )

                    timePickerDialog.show()
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }


        Button3.setOnClickListener {
            val c1 = Calendar.getInstance()
            val year = c1.get(Calendar.YEAR)
            val month = c1.get(Calendar.MONTH)
            val day = c1.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, monthOfYear, dayOfMonth ->

                    val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                    val timePickerDialog = TimePickerDialog(
                        requireContext(),
                        { _, hourOfDay, minute ->

                            val selectedTime = if (hourOfDay in 8 until 20) {
                                if (hourOfDay < 12) {
                                    "$hourOfDay:$minute AM"
                                } else {
                                    val hour = if (hourOfDay > 12) hourOfDay - 12 else hourOfDay
                                    "$hour:$minute PM"
                                }
                            } else {
                                // Reset the time if selected time is not between 8 AM and 8 PM
                                Toast.makeText(requireContext(), "Please select time between 8 AM and 8 PM", Toast.LENGTH_SHORT).show()
                                return@TimePickerDialog
                            }

                            Button3.text = "$selectedDate $selectedTime"
                        },
                        c1.get(Calendar.HOUR_OF_DAY), // Corrected here
                        c1.get(Calendar.MINUTE), // Corrected here
                        false
                    )

                    timePickerDialog.show()
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }





        val button4: Button = view.findViewById(R.id.button)
        button4.isEnabled = false

        button4.setOnClickListener{
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val button2DateText = Button2.text.toString().substringBefore(" ") // Extracting date part from Button2 text
            val button3DateText = Button3.text.toString().substringBefore(" ") // Extracting date part from Button3 text

            try {
                val button2Date = dateFormat.parse(button2DateText) // Parsing date from Button2 text
                val button3Date = dateFormat.parse(button3DateText) // Parsing date from Button3 text

                val differenceInMillis = button3Date.time - button2Date.time // Difference in milliseconds
                val differenceInDays = TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS) // Converting milliseconds to days


                DataHolder.myData = "$differenceInDays"
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val intent = Intent(requireContext(), MainActivity2::class.java)
                startActivity(intent)

        }

        mainBinding.button1.addTextChangedListener {
            updateButton4State(button4, mainBinding.button1.text.toString(), Button2.text.toString(), Button3.text.toString())
        }

        Button2.addTextChangedListener {
            updateButton4State(button4, mainBinding.button1.text.toString(), Button2.text.toString(), Button3.text.toString())
        }

        Button3.addTextChangedListener {
            updateButton4State(button4, mainBinding.button1.text.toString(), Button2.text.toString(), Button3.text.toString())
        }
        return view
    }
    private fun updateButton4State(button4: Button, text1: String, text2: String, text3: String) {
        button4.isEnabled = text1.isNotEmpty() && text2.isNotEmpty() && text3.isNotEmpty()
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val list: MutableList<Address>? =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        mainBinding.button1.text = "${list?.get(0)?.locality}"
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }
}


