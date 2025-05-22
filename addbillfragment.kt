class AddBillFragment : Fragment() {

    private lateinit var etAmount: EditText
    private lateinit var etDescription: EditText
    private lateinit var spinnerPayer: Spinner
    private lateinit var multiParticipants: MultiAutoCompleteTextView
    private lateinit var btnAddBill: Button

    private val people = listOf("Alice", "Bob", "Charlie") // Dummy names

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_bill, container, false)

        etAmount = view.findViewById(R.id.etAmount)
        etDescription = view.findViewById(R.id.etDescription)
        spinnerPayer = view.findViewById(R.id.spinnerPayer)
        multiParticipants = view.findViewById(R.id.multiParticipants)
        btnAddBill = view.findViewById(R.id.btnAddBill)

        setupUI()

        btnAddBill.setOnClickListener {
            addBill()
        }

        return view
    }

    private fun setupUI() {
        val payerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, people)
        payerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPayer.adapter = payerAdapter

        val participantsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, people)
        multiParticipants.setAdapter(participantsAdapter)
        multiParticipants.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
    }

    private fun addBill() {
        val amountText = etAmount.text.toString()
        val description = etDescription.text.toString()
        val payer = spinnerPayer.selectedItem.toString()
        val participants = multiParticipants.text.toString()
            .split(",").map { it.trim() }.filter { it.isNotEmpty() }

        if (amountText.isEmpty() || participants.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toDouble()

        // Save or display the bill data
        val message = "$payer paid ₹$amount for \"$description\" split among: ${participants.joinToString(", ")}"
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

        // You can store the bill in a global list or database (we'll do this soon)
    }
}
