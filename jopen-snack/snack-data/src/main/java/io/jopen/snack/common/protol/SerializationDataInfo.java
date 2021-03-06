package io.jopen.snack.common.protol;

@Deprecated
public final class SerializationDataInfo {
    private SerializationDataInfo() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions(
                (com.google.protobuf.ExtensionRegistryLite) registry);
    }

    public interface RpcExpressionOrBuilder extends
            // @@protoc_insertion_point(interface_extends:io.jopen.snack.common.protol.RpcExpression)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        java.util.List<com.google.protobuf.Any>
        getConditionsList();

        /**
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        com.google.protobuf.Any getConditions(int index);

        /**
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        int getConditionsCount();

        /**
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        java.util.List<? extends com.google.protobuf.AnyOrBuilder>
        getConditionsOrBuilderList();

        /**
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        com.google.protobuf.AnyOrBuilder getConditionsOrBuilder(
                int index);
    }

    /**
     * Protobuf type {@code io.jopen.snack.common.protol.RpcExpression}
     */
    public static final class RpcExpression extends
            com.google.protobuf.GeneratedMessageV3 implements
            // @@protoc_insertion_point(message_implements:io.jopen.snack.common.protol.RpcExpression)
            RpcExpressionOrBuilder {
        private static final long serialVersionUID = 0L;

        // Use RpcExpression.newBuilder() to construct.
        private RpcExpression(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private RpcExpression() {
            conditions_ = java.util.Collections.emptyList();
        }

        @Override
        @SuppressWarnings({"unused"})
        protected Object newInstance(
                UnusedPrivateParameter unused) {
            return new RpcExpression();
        }

        @Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }

        private RpcExpression(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10: {
                            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                                conditions_ = new java.util.ArrayList<com.google.protobuf.Any>();
                                mutable_bitField0_ |= 0x00000001;
                            }
                            conditions_.add(
                                    input.readMessage(com.google.protobuf.Any.parser(), extensionRegistry));
                            break;
                        }
                        default: {
                            if (!parseUnknownField(
                                    input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e).setUnfinishedMessage(this);
            } finally {
                if (((mutable_bitField0_ & 0x00000001) != 0)) {
                    conditions_ = java.util.Collections.unmodifiableList(conditions_);
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return SerializationDataInfo.internal_static_io_jopen_snack_common_protol_RpcExpression_descriptor;
        }

        @Override
        protected FieldAccessorTable
        internalGetFieldAccessorTable() {
            return SerializationDataInfo.internal_static_io_jopen_snack_common_protol_RpcExpression_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            RpcExpression.class, Builder.class);
        }

        public static final int CONDITIONS_FIELD_NUMBER = 1;
        private java.util.List<com.google.protobuf.Any> conditions_;

        /**
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        public java.util.List<com.google.protobuf.Any> getConditionsList() {
            return conditions_;
        }

        /**
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        public java.util.List<? extends com.google.protobuf.AnyOrBuilder>
        getConditionsOrBuilderList() {
            return conditions_;
        }

        /**
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        public int getConditionsCount() {
            return conditions_.size();
        }

        /**
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        public com.google.protobuf.Any getConditions(int index) {
            return conditions_.get(index);
        }

        /**
         * <code>repeated .google.protobuf.Any conditions = 1;</code>
         */
        public com.google.protobuf.AnyOrBuilder getConditionsOrBuilder(
                int index) {
            return conditions_.get(index);
        }

        private byte memoizedIsInitialized = -1;

        @Override
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) return true;
            if (isInitialized == 0) return false;

            memoizedIsInitialized = 1;
            return true;
        }

        @Override
        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            for (int i = 0; i < conditions_.size(); i++) {
                output.writeMessage(1, conditions_.get(i));
            }
            unknownFields.writeTo(output);
        }

        @Override
        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1) return size;

            size = 0;
            for (int i = 0; i < conditions_.size(); i++) {
                size += com.google.protobuf.CodedOutputStream
                        .computeMessageSize(1, conditions_.get(i));
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof RpcExpression)) {
                return super.equals(obj);
            }
            RpcExpression other = (RpcExpression) obj;

            if (!getConditionsList()
                    .equals(other.getConditionsList())) return false;
            if (!unknownFields.equals(other.unknownFields)) return false;
            return true;
        }

        @Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptor().hashCode();
            if (getConditionsCount() > 0) {
                hash = (37 * hash) + CONDITIONS_FIELD_NUMBER;
                hash = (53 * hash) + getConditionsList().hashCode();
            }
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static RpcExpression parseFrom(
                java.nio.ByteBuffer data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RpcExpression parseFrom(
                java.nio.ByteBuffer data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RpcExpression parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RpcExpression parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RpcExpression parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static RpcExpression parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static RpcExpression parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static RpcExpression parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static RpcExpression parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input);
        }

        public static RpcExpression parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static RpcExpression parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }

        public static RpcExpression parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        @Override
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(RpcExpression prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE
                    ? new Builder() : new Builder().mergeFrom(this);
        }

        @Override
        protected Builder newBuilderForType(
                BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        /**
         * Protobuf type {@code io.jopen.snack.common.protol.RpcExpression}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:io.jopen.snack.common.protol.RpcExpression)
                RpcExpressionOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return SerializationDataInfo.internal_static_io_jopen_snack_common_protol_RpcExpression_descriptor;
            }

            @Override
            protected FieldAccessorTable
            internalGetFieldAccessorTable() {
                return SerializationDataInfo.internal_static_io_jopen_snack_common_protol_RpcExpression_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                RpcExpression.class, Builder.class);
            }

            // Construct using io.jopen.snack.common.protol.SerializationDataInfo.RpcExpression.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessageV3
                        .alwaysUseFieldBuilders) {
                    getConditionsFieldBuilder();
                }
            }

            @Override
            public Builder clear() {
                super.clear();
                if (conditionsBuilder_ == null) {
                    conditions_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000001);
                } else {
                    conditionsBuilder_.clear();
                }
                return this;
            }

            @Override
            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return SerializationDataInfo.internal_static_io_jopen_snack_common_protol_RpcExpression_descriptor;
            }

            @Override
            public RpcExpression getDefaultInstanceForType() {
                return RpcExpression.getDefaultInstance();
            }

            @Override
            public RpcExpression build() {
                RpcExpression result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            @Override
            public RpcExpression buildPartial() {
                RpcExpression result = new RpcExpression(this);
                int from_bitField0_ = bitField0_;
                if (conditionsBuilder_ == null) {
                    if (((bitField0_ & 0x00000001) != 0)) {
                        conditions_ = java.util.Collections.unmodifiableList(conditions_);
                        bitField0_ = (bitField0_ & ~0x00000001);
                    }
                    result.conditions_ = conditions_;
                } else {
                    result.conditions_ = conditionsBuilder_.build();
                }
                onBuilt();
                return result;
            }

            @Override
            public Builder clone() {
                return super.clone();
            }

            @Override
            public Builder setField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return super.setField(field, value);
            }

            @Override
            public Builder clearField(
                    com.google.protobuf.Descriptors.FieldDescriptor field) {
                return super.clearField(field);
            }

            @Override
            public Builder clearOneof(
                    com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                return super.clearOneof(oneof);
            }

            @Override
            public Builder setRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    int index, Object value) {
                return super.setRepeatedField(field, index, value);
            }

            @Override
            public Builder addRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    Object value) {
                return super.addRepeatedField(field, value);
            }

            @Override
            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof RpcExpression) {
                    return mergeFrom((RpcExpression) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(RpcExpression other) {
                if (other == RpcExpression.getDefaultInstance()) return this;
                if (conditionsBuilder_ == null) {
                    if (!other.conditions_.isEmpty()) {
                        if (conditions_.isEmpty()) {
                            conditions_ = other.conditions_;
                            bitField0_ = (bitField0_ & ~0x00000001);
                        } else {
                            ensureConditionsIsMutable();
                            conditions_.addAll(other.conditions_);
                        }
                        onChanged();
                    }
                } else {
                    if (!other.conditions_.isEmpty()) {
                        if (conditionsBuilder_.isEmpty()) {
                            conditionsBuilder_.dispose();
                            conditionsBuilder_ = null;
                            conditions_ = other.conditions_;
                            bitField0_ = (bitField0_ & ~0x00000001);
                            conditionsBuilder_ =
                                    com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                                            getConditionsFieldBuilder() : null;
                        } else {
                            conditionsBuilder_.addAllMessages(other.conditions_);
                        }
                    }
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            @Override
            public final boolean isInitialized() {
                return true;
            }

            @Override
            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                RpcExpression parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (RpcExpression) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int bitField0_;

            private java.util.List<com.google.protobuf.Any> conditions_ =
                    java.util.Collections.emptyList();

            private void ensureConditionsIsMutable() {
                if (!((bitField0_ & 0x00000001) != 0)) {
                    conditions_ = new java.util.ArrayList<com.google.protobuf.Any>(conditions_);
                    bitField0_ |= 0x00000001;
                }
            }

            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder> conditionsBuilder_;

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public java.util.List<com.google.protobuf.Any> getConditionsList() {
                if (conditionsBuilder_ == null) {
                    return java.util.Collections.unmodifiableList(conditions_);
                } else {
                    return conditionsBuilder_.getMessageList();
                }
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public int getConditionsCount() {
                if (conditionsBuilder_ == null) {
                    return conditions_.size();
                } else {
                    return conditionsBuilder_.getCount();
                }
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public com.google.protobuf.Any getConditions(int index) {
                if (conditionsBuilder_ == null) {
                    return conditions_.get(index);
                } else {
                    return conditionsBuilder_.getMessage(index);
                }
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder setConditions(
                    int index, com.google.protobuf.Any value) {
                if (conditionsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureConditionsIsMutable();
                    conditions_.set(index, value);
                    onChanged();
                } else {
                    conditionsBuilder_.setMessage(index, value);
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder setConditions(
                    int index, com.google.protobuf.Any.Builder builderForValue) {
                if (conditionsBuilder_ == null) {
                    ensureConditionsIsMutable();
                    conditions_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    conditionsBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder addConditions(com.google.protobuf.Any value) {
                if (conditionsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureConditionsIsMutable();
                    conditions_.add(value);
                    onChanged();
                } else {
                    conditionsBuilder_.addMessage(value);
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder addConditions(
                    int index, com.google.protobuf.Any value) {
                if (conditionsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureConditionsIsMutable();
                    conditions_.add(index, value);
                    onChanged();
                } else {
                    conditionsBuilder_.addMessage(index, value);
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder addConditions(
                    com.google.protobuf.Any.Builder builderForValue) {
                if (conditionsBuilder_ == null) {
                    ensureConditionsIsMutable();
                    conditions_.add(builderForValue.build());
                    onChanged();
                } else {
                    conditionsBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder addConditions(
                    int index, com.google.protobuf.Any.Builder builderForValue) {
                if (conditionsBuilder_ == null) {
                    ensureConditionsIsMutable();
                    conditions_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    conditionsBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder addAllConditions(
                    Iterable<? extends com.google.protobuf.Any> values) {
                if (conditionsBuilder_ == null) {
                    ensureConditionsIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(
                            values, conditions_);
                    onChanged();
                } else {
                    conditionsBuilder_.addAllMessages(values);
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder clearConditions() {
                if (conditionsBuilder_ == null) {
                    conditions_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000001);
                    onChanged();
                } else {
                    conditionsBuilder_.clear();
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public Builder removeConditions(int index) {
                if (conditionsBuilder_ == null) {
                    ensureConditionsIsMutable();
                    conditions_.remove(index);
                    onChanged();
                } else {
                    conditionsBuilder_.remove(index);
                }
                return this;
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public com.google.protobuf.Any.Builder getConditionsBuilder(
                    int index) {
                return getConditionsFieldBuilder().getBuilder(index);
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public com.google.protobuf.AnyOrBuilder getConditionsOrBuilder(
                    int index) {
                if (conditionsBuilder_ == null) {
                    return conditions_.get(index);
                } else {
                    return conditionsBuilder_.getMessageOrBuilder(index);
                }
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public java.util.List<? extends com.google.protobuf.AnyOrBuilder>
            getConditionsOrBuilderList() {
                if (conditionsBuilder_ != null) {
                    return conditionsBuilder_.getMessageOrBuilderList();
                } else {
                    return java.util.Collections.unmodifiableList(conditions_);
                }
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public com.google.protobuf.Any.Builder addConditionsBuilder() {
                return getConditionsFieldBuilder().addBuilder(
                        com.google.protobuf.Any.getDefaultInstance());
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public com.google.protobuf.Any.Builder addConditionsBuilder(
                    int index) {
                return getConditionsFieldBuilder().addBuilder(
                        index, com.google.protobuf.Any.getDefaultInstance());
            }

            /**
             * <code>repeated .google.protobuf.Any conditions = 1;</code>
             */
            public java.util.List<com.google.protobuf.Any.Builder>
            getConditionsBuilderList() {
                return getConditionsFieldBuilder().getBuilderList();
            }

            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder>
            getConditionsFieldBuilder() {
                if (conditionsBuilder_ == null) {
                    conditionsBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<>(
                            conditions_,
                            ((bitField0_ & 0x00000001) != 0),
                            getParentForChildren(),
                            isClean());
                    conditions_ = null;
                }
                return conditionsBuilder_;
            }

            @Override
            public final Builder setUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.setUnknownFields(unknownFields);
            }

            @Override
            public final Builder mergeUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }


            // @@protoc_insertion_point(builder_scope:io.jopen.snack.common.protol.RpcExpression)
        }

        // @@protoc_insertion_point(class_scope:io.jopen.snack.common.protol.RpcExpression)
        private static final RpcExpression DEFAULT_INSTANCE;

        static {
            DEFAULT_INSTANCE = new RpcExpression();
        }

        public static RpcExpression getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        private static final com.google.protobuf.Parser<RpcExpression>
                PARSER = new com.google.protobuf.AbstractParser<RpcExpression>() {
            @Override
            public RpcExpression parsePartialFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return new RpcExpression(input, extensionRegistry);
            }
        };

        public static com.google.protobuf.Parser<RpcExpression> parser() {
            return PARSER;
        }

        @Override
        public com.google.protobuf.Parser<RpcExpression> getParserForType() {
            return PARSER;
        }

        @Override
        public RpcExpression getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

    }

    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_io_jopen_snack_common_protol_RpcExpression_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_io_jopen_snack_common_protol_RpcExpression_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }

    private static com.google.protobuf.Descriptors.FileDescriptor
            descriptor;

    static {
        String[] descriptorData = {
                "\n\023rpcExpression.proto\022\034io.jopen.snack.co" +
                        "mmon.protol\032\022protobuf/any.proto\"9\n\rRpcEx" +
                        "pression\022(\n\nconditions\030\001 \003(\0132\024.google.pr" +
                        "otobuf.AnyB\027B\025SerializationDataInfob\006pro" +
                        "to3"
        };
        descriptor = com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[]{
                                com.google.protobuf.AnyProto.getDescriptor(),
                        });
        internal_static_io_jopen_snack_common_protol_RpcExpression_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_io_jopen_snack_common_protol_RpcExpression_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_io_jopen_snack_common_protol_RpcExpression_descriptor,
                new String[]{"Conditions",});
        com.google.protobuf.AnyProto.getDescriptor();
    }

    // @@protoc_insertion_point(outer_class_scope)
}
